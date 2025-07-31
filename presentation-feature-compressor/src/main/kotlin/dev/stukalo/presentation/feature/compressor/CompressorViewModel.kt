package dev.stukalo.presentation.feature.compressor

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.ContentScale.Companion.Fit
import dev.stukalo.domain.usecase.android.base.BaseCompressorJavaUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingAsyncTaskUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingCoroutinesUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingJavaUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEAsyncTaskUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLECoroutinesUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEJavaUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEUseCase
import dev.stukalo.domain.usecase.android.decompression.rle.DecompressByteArrayRLEUseCase
import dev.stukalo.domain.usecase.android.filestore.SaveImageDataToInternalStorageUseCase
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.model.AsyncMethod
import dev.stukalo.presentation.core.ui.platform.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import java.nio.ByteBuffer
import kotlin.properties.Delegates
import kotlin.reflect.KProperty
import kotlin.system.measureTimeMillis


internal class CompressorViewModel(
    private val compressBitmapRLEAsyncTaskUseCase: CompressBitmapRLEAsyncTaskUseCase,
    private val compressBitmapRLECoroutinesUseCase: CompressBitmapRLECoroutinesUseCase,
    private val compressBitmapRLEJavaUseCase: CompressBitmapRLEJavaUseCase,
    private val compressBitmapRLEUseCase: CompressBitmapRLEUseCase,

    private val compressBitmapDownsamplingAsyncTaskUseCase: CompressBitmapDownsamplingAsyncTaskUseCase,
    private val compressBitmapDownsamplingCoroutinesUseCase: CompressBitmapDownsamplingCoroutinesUseCase,
    private val compressBitmapDownsamplingJavaUseCase: CompressBitmapDownsamplingJavaUseCase,
    private val compressBitmapDownsamplingUseCase: CompressBitmapDownsamplingUseCase,

    private val saveImageDataToInternalStorageUseCase: SaveImageDataToInternalStorageUseCase,

    private val decompressByteArrayRLEUseCase: DecompressByteArrayRLEUseCase,
) : BaseViewModel<CompressorScreenUiState, CompressorScreenViewIntent, CompressorScreenSingleEvent>() {

    override val _state: MutableStateFlow<CompressorScreenUiState> =
        MutableStateFlow(CompressorScreenUiState.initial())
    override val state: StateFlow<CompressorScreenUiState> = _state.asStateFlow()

    override val _singleEvent: MutableSharedFlow<CompressorScreenSingleEvent> = MutableSharedFlow()
    override val singleEvent: SharedFlow<CompressorScreenSingleEvent> = _singleEvent.asSharedFlow()

    private var originalImageBitmap: Bitmap? = null

    private val selectedAlgorithm = MutableStateFlow(Algorithm.None)
    private var selectedAsyncMethod = MutableStateFlow(AsyncMethod.Coroutines)
    private var compressionLevel = MutableStateFlow(50)

    private var lastCompressedImageData: ByteArray? = null
    private lateinit var lastUsedAlgorithm: Algorithm
    private lateinit var lastUsedAsyncMethod: AsyncMethod
    private var lastCompressionTime by Delegates.notNull<Int>()

    init {
        launch(
            coroutineContext = Dispatchers.Default
        ) {
            compressImageOnParamsChanged()
        }
    }

    override fun consumeIntent(intent: CompressorScreenViewIntent) {
        launch(
            coroutineContext = Dispatchers.Default
        ) {
            when (intent) {
                is CompressorScreenViewIntent.OnLaunch -> setImageBitmap(intent.originalImageBitmap)

                is CompressorScreenViewIntent.OnAlgorithmPagerScroll -> handleAlgorithmPagerScroll(
                    intent.page
                )

                is CompressorScreenViewIntent.OnAsyncMethodPagerScroll -> handleAsyncMethodPagerScroll(
                    intent.page
                )

                is CompressorScreenViewIntent.OnChangeCompressionLevel -> updateCompressionLevel(
                    intent.level.toInt()
                )

                CompressorScreenViewIntent.OnChangeCompressionVisibility -> toggleVisibility {
                    it.copy(isCompressionSliderVisible = !it.isCompressionSliderVisible)
                }

                CompressorScreenViewIntent.OnChangeAlgorithmVisibility -> toggleVisibility {
                    it.copy(isAlgorithmPagerVisible = !it.isAlgorithmPagerVisible)
                }

                CompressorScreenViewIntent.OnChangeAsyncVisibility -> toggleVisibility {
                    it.copy(isAsyncPagerVisible = !it.isAsyncPagerVisible)
                }

                CompressorScreenViewIntent.OnNavigateToCompareClick -> handleNavigateToCompareClick()

                CompressorScreenViewIntent.NavigateToLogsClick -> handleNavigateToLogsClick()

                CompressorScreenViewIntent.OnDownloadClick -> handleDownloadClick()

                CompressorScreenViewIntent.OnChangeContentScale -> changeContentScale()

                CompressorScreenViewIntent.OnNavigateBackClick -> handleNavigateBackClick()
            }
        }
    }

    private fun setImageBitmap(originalImageBitmap: Bitmap) {
        if (this.originalImageBitmap == null) {
            _state.update {
                it.copy(isLoading = false, processedImage = originalImageBitmap)
            }
            this.originalImageBitmap = originalImageBitmap
        }
    }

    @OptIn(FlowPreview::class)
    private suspend fun compressImageOnParamsChanged() {
        combine(selectedAlgorithm, selectedAsyncMethod, compressionLevel) { algorithm, asyncMethod, level ->
            Triple(algorithm, asyncMethod, level)
        }.drop(1).debounce(1200).collectLatest { params ->
            processImage(params)
        }
    }

    private suspend fun processImage(params: Triple<Algorithm, AsyncMethod, Int>) {
        val (algorithm, asyncMethod, level) = params
        originalImageBitmap?.let { originalBitmap ->
            _state.update { it.copy(isImageProcessing = true) }

            val processedBitmap = when (algorithm) {
                Algorithm.RunLengthEncoding -> processWithRLE(originalBitmap, asyncMethod)
                Algorithm.Downsampling -> processWithDownsampling(originalBitmap, asyncMethod, level)
                Algorithm.DownsamplingRLE -> processWithDownsamplingRLE(originalBitmap, asyncMethod, level)
                Algorithm.None -> {
                    lastCompressedImageData = null
                    originalImageBitmap
                }
            }

            _state.update {
                it.copy(isImageProcessing = false, processedImage = processedBitmap)
            }
        }
    }

    private suspend fun processWithRLE(originalBitmap: Bitmap, asyncMethod: AsyncMethod): Bitmap {
        val startTime = System.currentTimeMillis()
        lastCompressedImageData = when (asyncMethod) {
            AsyncMethod.Coroutines -> compressBitmapRLECoroutinesUseCase(originalBitmap)
            AsyncMethod.AsyncTask -> compressBitmapRLEAsyncTaskUseCase(originalBitmap)
            AsyncMethod.Java -> compressBitmapRLEJavaUseCase(originalBitmap)
            AsyncMethod.NonAsyncRun -> compressBitmapRLEUseCase(originalBitmap)
        }

        lastCompressionTime = (System.currentTimeMillis() - startTime).toInt()
        lastUsedAlgorithm = Algorithm.RunLengthEncoding
        lastUsedAsyncMethod = asyncMethod

        return decompressByteArrayRLEUseCase(
            lastCompressedImageData!!,
            originalBitmap.width,
            originalBitmap.height
        )
    }

    private suspend fun processWithDownsampling(
        originalBitmap: Bitmap,
        asyncMethod: AsyncMethod,
        level: Int
    ): Bitmap {
        val startTime = System.currentTimeMillis()
        val compressedBitmap = when (asyncMethod) {
            AsyncMethod.Coroutines -> compressBitmapDownsamplingCoroutinesUseCase(originalBitmap, level)
            AsyncMethod.AsyncTask -> compressBitmapDownsamplingAsyncTaskUseCase(originalBitmap, level)
            AsyncMethod.Java -> compressBitmapDownsamplingJavaUseCase(originalBitmap, level)
            AsyncMethod.NonAsyncRun -> compressBitmapDownsamplingUseCase(originalBitmap, level)
        }

        lastCompressionTime = (System.currentTimeMillis() - startTime).toInt()

        lastUsedAlgorithm = Algorithm.Downsampling
        lastUsedAsyncMethod = asyncMethod

        lastCompressedImageData = bitmapToByteArray(compressedBitmap)

        return compressedBitmap
    }

    private suspend fun processWithDownsamplingRLE(
        originalBitmap: Bitmap,
        asyncMethod: AsyncMethod,
        level: Int,
    ): Bitmap {
        val startTime = System.currentTimeMillis()

        val compressedBitmap = when (asyncMethod) {
            AsyncMethod.Coroutines -> compressBitmapDownsamplingCoroutinesUseCase(originalBitmap, level)
            AsyncMethod.AsyncTask -> compressBitmapDownsamplingAsyncTaskUseCase(originalBitmap, level)
            AsyncMethod.Java -> compressBitmapDownsamplingJavaUseCase(originalBitmap, level)
            AsyncMethod.NonAsyncRun -> compressBitmapDownsamplingUseCase(originalBitmap, level)
        }

        lastCompressedImageData = when (asyncMethod) {
            AsyncMethod.Coroutines -> compressBitmapRLECoroutinesUseCase(compressedBitmap)
            AsyncMethod.AsyncTask -> compressBitmapRLEAsyncTaskUseCase(compressedBitmap)
            AsyncMethod.Java -> compressBitmapRLEJavaUseCase(compressedBitmap)
            AsyncMethod.NonAsyncRun -> compressBitmapRLEUseCase(compressedBitmap)
        }

        lastCompressionTime = (System.currentTimeMillis() - startTime).toInt()
        lastUsedAlgorithm = Algorithm.DownsamplingRLE
        lastUsedAsyncMethod = asyncMethod

        return decompressByteArrayRLEUseCase(
            lastCompressedImageData!!,
            compressedBitmap.width,
            compressedBitmap.height
        )
    }

    private fun handleAlgorithmPagerScroll(page: Int) {
        selectedAlgorithm.value = state.value.algorithmPagerList[page]
        _state.update {
            it.copy(
                isCompressionViewVisible = selectedAlgorithm.value != Algorithm.RunLengthEncoding
                        && selectedAlgorithm.value != Algorithm.None,
                isAsyncViewVisible = selectedAlgorithm.value != Algorithm.None,
            )
        }
    }

    private fun handleAsyncMethodPagerScroll(page: Int) {
        selectedAsyncMethod.value = state.value.asyncMethodPagerList[page]
    }

    private fun updateCompressionLevel(level: Int) {
        compressionLevel.value = level
        _state.update { it.copy(compressionLevel = level) }
    }

    private fun toggleVisibility(update: (CompressorScreenUiState) -> CompressorScreenUiState) {
        _state.update(update)
    }

    private fun handleNavigateBackClick() {
        emitSingleEvent(CompressorScreenSingleEvent.NavigateBack)
    }

    private fun handleNavigateToCompareClick() {
        state.value.processedImage?.let { image ->
            lastCompressedImageData?.let {
                emitSingleEvent(CompressorScreenSingleEvent.NavigateToCompareClick(image))
            } ?: emitSingleEvent(CompressorScreenSingleEvent.Error("Image is not compressed yet!"))
        }
    }

    private fun handleNavigateToLogsClick() {
        state.value.processedImage?.let { image ->
            lastCompressedImageData?.let { lastCompressedImageData ->
                emitSingleEvent(
                    CompressorScreenSingleEvent.NavigateToLogsClick(
                        processedImage = image,
                        processedImageBytes =
                            if(lastUsedAlgorithm != Algorithm.Downsampling) lastCompressedImageData.size
                            else image.byteCount,
                        usedAlgorithm = lastUsedAlgorithm,
                        usedAsyncMethod = lastUsedAsyncMethod,
                        compressionTime = lastCompressionTime,
                        compressionLevel = compressionLevel.value
                    )
                )
            } ?: emitSingleEvent(CompressorScreenSingleEvent.Error("Image is not compressed yet!"))
        }
    }

    private fun handleDownloadClick() {
        state.value.processedImage?.let { image ->
            lastCompressedImageData?.let { lastCompressedImageData ->
                saveImageDataToInternalStorage(lastCompressedImageData, image.width, image.height)
                emitSingleEvent(CompressorScreenSingleEvent.Error("Image downloading..."))
            } ?: emitSingleEvent(CompressorScreenSingleEvent.Error("Image is not compressed yet!"))
        }
    }

    private fun changeContentScale() {
        _state.update {
            it.copy(
                contentScale = if (it.contentScale == Crop) Fit else Crop
            )
        }
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
        bitmap.copyPixelsToBuffer(byteBuffer)
        byteBuffer.rewind()
        return byteBuffer.array()
    }

    private fun saveImageDataToInternalStorage(
        byteArray: ByteArray,
        width: Int,
        height: Int,
        name: String? = null
    ) {
        saveImageDataToInternalStorageUseCase(
            byteArray = byteArray,
            compressionMethod = if (lastUsedAlgorithm == Algorithm.Downsampling) DS else RLE,
            width = width,
            height = height,
            name = name
        )
    }





// ______________________Diploma work section______________________

    private fun originalBitmap(bitmap: Bitmap){
        val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
        bitmap.copyPixelsToBuffer(byteBuffer)
        byteBuffer.rewind()
        val bArray = byteBuffer.array()

        saveImageDataToInternalStorage(bArray, bitmap.width, bitmap.height, "original_image")
    }

    inner class RleCompressor(compressor: dev.stukalo.domain.usecase.android.compression.base.BaseRleCompressorUseCase) : dev.stukalo.domain.usecase.android.compression.base.BaseRleCompressorUseCase by compressor {

        suspend fun averageComputationTime(
            bitmap: Bitmap,
            n: Int = 20,
        ) {
            val t = measureTimeMillis {
                repeat(n) {
                    invoke(bitmap)
                }
            }

            Log.d("Average time: ", (t / n).toString())

        }

    }

    private fun checkMethodAverageComputationTime(
        compressorUseCase: BaseCompressorJavaUseCase,
        bitmap: Bitmap,
        n: Int = 20,
    ) {
        val t = measureTimeMillis {
            repeat(n) {
                compressorUseCase(bitmap)
            }
        }

        Log.d("Average time: ", (t / n).toString())

    }

    class RememberLazyState<in T, out R>(
        private val initialize: () -> T
    ) where T: State<R>, R: Any? {

        private var state: T? = null

        @Composable
        private fun setValue(value: T) {
            this.state = remember { value }
        }

        @Composable
        operator fun getValue(thisRef: Any?, property: KProperty<*>): R =
            if (state == null) {
                setValue(initialize())
                state!!.value
            } else
                state!!.value

    }
}

const val RLE = "RLE"
const val DS = "DS"
