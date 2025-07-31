package dev.stukalo.presentation.feature.logs

import android.graphics.Bitmap
import dev.stukalo.presentation.core.ui.platform.BaseViewModel
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingAsyncTaskUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingCoroutinesUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingJavaUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleAsyncTaskUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleCoroutinesUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleJavaUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEAsyncTaskUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLECoroutinesUseCase
import dev.stukalo.domain.usecase.android.decompression.rle.DecompressByteArrayRLEUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEJavaUseCase
import dev.stukalo.presentation.feature.logs.model.ChartType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis
import androidx.core.graphics.scale
import androidx.core.graphics.get

internal class LogsViewModel(
    private val compressBitmapRLEAsyncTaskUseCase: CompressBitmapRLEAsyncTaskUseCase,
    private val compressBitmapRLECoroutinesUseCase: CompressBitmapRLECoroutinesUseCase,
    private val compressBitmapRLEJavaUseCase: CompressBitmapRLEJavaUseCase,
    private val compressBitmapRLEUseCase: CompressBitmapRLEUseCase,

    private val compressBitmapDownsamplingAsyncTaskUseCase: CompressBitmapDownsamplingAsyncTaskUseCase,
    private val compressBitmapDownsamplingCoroutinesUseCase: CompressBitmapDownsamplingCoroutinesUseCase,
    private val compressBitmapDownsamplingJavaUseCase: CompressBitmapDownsamplingJavaUseCase,
    private val compressBitmapDownsamplingUseCase: CompressBitmapDownsamplingUseCase,

    private val compressBitmapDownsamplingRleAsyncTaskUseCase: CompressBitmapDownsamplingRleAsyncTaskUseCase,
    private val compressBitmapDownsamplingRleCoroutinesUseCase: CompressBitmapDownsamplingRleCoroutinesUseCase,
    private val compressBitmapDownsamplingRleJavaUseCase: CompressBitmapDownsamplingRleJavaUseCase,
    private val compressBitmapDownsamplingRleUseCase: CompressBitmapDownsamplingRleUseCase,

    private val decompressByteArrayRLEUseCase: DecompressByteArrayRLEUseCase,
) : BaseViewModel<LogsScreenScreenState, LogsScreenViewIntent, LogsScreenSingleEvent>() {
    override val _state: MutableStateFlow<LogsScreenScreenState> =
        MutableStateFlow(LogsScreenScreenState.initial())
    override val state: StateFlow<LogsScreenScreenState> = _state.asStateFlow()
    override val _singleEvent: MutableSharedFlow<LogsScreenSingleEvent> = MutableSharedFlow()
    override val singleEvent: SharedFlow<LogsScreenSingleEvent> = _singleEvent.asSharedFlow()

    private var nonAsyncCompressionTime = 0L
    private var asyncTaskCompressionTime = 0L
    private var coroutinesCompressionTime = 0L
    private var javaCompressionTime = 0L

    override fun consumeIntent(intent: LogsScreenViewIntent) {
        launch(
            coroutineContext = Dispatchers.Default
        ) {
            when(intent) {
                is LogsScreenViewIntent.OnSelectChartType -> {
                    emitSingleEvent(LogsScreenSingleEvent.OnChartTypeSelected(intent.chartType))
                }

                is LogsScreenViewIntent.TimeChartTypeSelected -> with(intent) {
                    compareDifferentAlgorithmImplTime(
                        originalBitmap = originalBitmap,
                        usedAlgorithm = usedAlgorithm,
                        compressionLevel = compressionLevel,
                    )
                }

                is LogsScreenViewIntent.MSEChartTypeSelected -> with(intent) {
                    compareMSEWithOtherAlgorithms(
                        originalBitmap = originalBitmap,
                        compressedBitmap = compressedBitmap,
                        usedAlgorithm = usedAlgorithm,
                        compressionLevel = compressionLevel
                    )
                }

                is LogsScreenViewIntent.CRChartTypeSelected -> with(intent) {
                    compareCRWithOtherAlgorithms(
                        originalBitmap = originalBitmap,
                        compressedBitmapSize = compressedBitmapSize,
                        usedAlgorithm = usedAlgorithm,
                        compressionLevel = compressionLevel
                    )
                }

                LogsScreenViewIntent.OnNavigateBack -> emitSingleEvent(LogsScreenSingleEvent.NavigateBack)
            }
        }
    }

    private suspend fun compareDifferentAlgorithmImplTime(
        originalBitmap: Bitmap,
        usedAlgorithm: Algorithm,
        compressionLevel: Int,
    ) {
        _state.update {
            it.copy(isLoading = true, selectedChartType = ChartType.TIME)
        }

        nonAsyncCompressionTime = when (usedAlgorithm) {
            Algorithm.RunLengthEncoding -> measureTimeMillis {
                compressBitmapRLEUseCase(originalBitmap)
            }

            Algorithm.Downsampling -> measureTimeMillis {
                compressBitmapDownsamplingUseCase(originalBitmap, compressionLevel)
            }

            Algorithm.DownsamplingRLE -> measureTimeMillis {
                compressBitmapDownsamplingRleUseCase(originalBitmap, compressionLevel)
            }

            Algorithm.None -> 0
        }

        asyncTaskCompressionTime = when (usedAlgorithm) {
            Algorithm.RunLengthEncoding -> measureTimeMillis {
                compressBitmapRLEAsyncTaskUseCase(originalBitmap)
            }

            Algorithm.Downsampling -> measureTimeMillis {
                compressBitmapDownsamplingAsyncTaskUseCase(originalBitmap, compressionLevel)
            }

            Algorithm.DownsamplingRLE -> measureTimeMillis {
                compressBitmapDownsamplingRleAsyncTaskUseCase(originalBitmap, compressionLevel)
            }

            Algorithm.None -> 0
        }

        coroutinesCompressionTime = when (usedAlgorithm) {
            Algorithm.RunLengthEncoding -> measureTimeMillis {
                compressBitmapRLECoroutinesUseCase(originalBitmap)
            }

            Algorithm.Downsampling -> measureTimeMillis {
                compressBitmapDownsamplingCoroutinesUseCase(originalBitmap, compressionLevel)
            }

            Algorithm.DownsamplingRLE -> measureTimeMillis {
                compressBitmapDownsamplingRleCoroutinesUseCase(originalBitmap, compressionLevel)
            }

            Algorithm.None -> 0
        }

        javaCompressionTime = when (usedAlgorithm) {
            Algorithm.RunLengthEncoding -> measureTimeMillis {
                compressBitmapRLEJavaUseCase(originalBitmap)
            }

            Algorithm.Downsampling -> measureTimeMillis {
                compressBitmapDownsamplingJavaUseCase(originalBitmap, compressionLevel)
            }

            Algorithm.DownsamplingRLE -> measureTimeMillis {
                compressBitmapDownsamplingRleJavaUseCase(originalBitmap, compressionLevel)
            }

            Algorithm.None -> 0
        }

        _state.update {
            it.copy(
                isLoading = false,
                asyncTaskCompressionTime = asyncTaskCompressionTime,
                coroutinesCompressionTime = coroutinesCompressionTime,
                javaCompressionTime = javaCompressionTime,
                nonAsyncCompressionTime = nonAsyncCompressionTime,

            )
        }
    }


    private suspend fun compareCRWithOtherAlgorithms(
        originalBitmap: Bitmap,
        compressedBitmapSize: Int,
        usedAlgorithm: Algorithm,
        compressionLevel: Int,
    ) {
        _state.update {
            it.copy(isLoading = true, selectedChartType = ChartType.CR)
        }

        val originalBitmapSize = originalBitmap.byteCount.toFloat()

        when(usedAlgorithm) {
            Algorithm.RunLengthEncoding -> _state.update {
                it.copy(
                    isLoading = false,
                    rleCR = originalBitmapSize / compressedBitmapSize,
                    downsamplingCR = originalBitmapSize /
                            compressBitmapDownsamplingCoroutinesUseCase(
                                originalBitmap,
                                compressionLevel
                            ).byteCount,
                    downsamplingRleCR = originalBitmapSize /
                            compressBitmapDownsamplingRleCoroutinesUseCase(
                                originalBitmap,
                                compressionLevel
                            ).size,
                )
            }

            Algorithm.Downsampling -> _state.update {
                it.copy(
                    isLoading = false,
                    rleCR = originalBitmapSize / compressBitmapRLECoroutinesUseCase(originalBitmap).size,
                    downsamplingCR = originalBitmapSize / compressedBitmapSize,
                    downsamplingRleCR = originalBitmapSize /
                            compressBitmapDownsamplingRleCoroutinesUseCase(
                                originalBitmap,
                                compressionLevel
                            ).size,
                )
            }

            Algorithm.DownsamplingRLE -> _state.update {
                it.copy(
                    isLoading = false,
                    rleCR = originalBitmapSize /
                            compressBitmapRLECoroutinesUseCase(
                                originalBitmap
                            ).size,
                    downsamplingCR = originalBitmapSize /
                            compressBitmapDownsamplingCoroutinesUseCase(
                                originalBitmap,
                                compressionLevel
                            ).byteCount,
                    downsamplingRleCR = originalBitmapSize / compressedBitmapSize,
                )
            }

            Algorithm.None -> Unit
        }
    }


    private suspend fun compareMSEWithOtherAlgorithms(
        originalBitmap: Bitmap,
        compressedBitmap: Bitmap,
        usedAlgorithm: Algorithm,
        compressionLevel: Int,
    ) {
        _state.update {
            it.copy(isLoading = true, selectedChartType = ChartType.MSE)
        }

        // Scale the compressed bitmap to match the original bitmap's dimensions
        val scaledCompressedBitmap =
            compressedBitmap.scale(originalBitmap.width, originalBitmap.height)

        val smallBitmap = compressBitmapDownsamplingCoroutinesUseCase(
            originalBitmap,
            compressionLevel,
        )

        val sbWidth = smallBitmap.width
        val sbHeight = smallBitmap.height

        val rleMSE: Float
        val downsamplingMSE: Float
        val downsamplingRleMSE: Float

        when (usedAlgorithm) {
            Algorithm.RunLengthEncoding -> {
                rleMSE = calculateMSE(originalBitmap, scaledCompressedBitmap)

                downsamplingMSE = calculateMSE(
                    originalBitmap,
                    compressBitmapDownsamplingCoroutinesUseCase(
                        originalBitmap,
                        compressionLevel
                    ).scale(originalBitmap.width, originalBitmap.height)
                )

                downsamplingRleMSE = calculateMSE(
                    originalBitmap,
                    decompressByteArrayRLEUseCase(
                        compressBitmapDownsamplingRleCoroutinesUseCase(
                            originalBitmap,
                            compressionLevel,
                        ),
                        sbWidth,
                        sbHeight,
                    ).scale(originalBitmap.width, originalBitmap.height)
                )
            }

            Algorithm.Downsampling -> {
                rleMSE = calculateMSE(
                    originalBitmap,
                    decompressByteArrayRLEUseCase(
                        compressBitmapRLECoroutinesUseCase(originalBitmap),
                        originalBitmap.width,
                        originalBitmap.height,
                    )
                )

                downsamplingMSE = calculateMSE(originalBitmap, scaledCompressedBitmap)

                downsamplingRleMSE = calculateMSE(
                    originalBitmap,
                    decompressByteArrayRLEUseCase(
                        compressBitmapDownsamplingRleCoroutinesUseCase(
                            originalBitmap,
                            compressionLevel,
                        ),
                        sbWidth,
                        sbHeight,
                    ).scale(originalBitmap.width, originalBitmap.height)
                )
            }

            Algorithm.DownsamplingRLE -> {
                rleMSE = calculateMSE(
                    originalBitmap,
                    decompressByteArrayRLEUseCase(
                        compressBitmapRLECoroutinesUseCase(originalBitmap),
                        originalBitmap.width,
                        originalBitmap.height,
                    )
                )

                downsamplingMSE = calculateMSE(
                    originalBitmap,
                    compressBitmapDownsamplingCoroutinesUseCase(
                        originalBitmap,
                        compressionLevel
                    ).scale(originalBitmap.width, originalBitmap.height)
                )

                downsamplingRleMSE = calculateMSE(originalBitmap, scaledCompressedBitmap)
            }

            Algorithm.None -> {
                rleMSE = 0F
                downsamplingMSE = 0F
                downsamplingRleMSE = 0F
            }
        }

        _state.update {
            it.copy(
                isLoading = false,
                rleMSE = rleMSE,
                downsamplingMSE = downsamplingMSE,
                downsamplingRleMSE = downsamplingRleMSE,
            )
        }
    }

    private suspend fun calculateMSE(original: Bitmap, compressed: Bitmap): Float {
        val width = original.width
        val height = original.height
        val mse = AtomicInteger(0)

        coroutineScope {
            for (y in 0 until height) {
                launch {
                    var localMSE = 0
                    for (x in 0 until width) {
                        val originalPixel = original[x, y]
                        val compressedPixel = compressed[x, y]

                        // Extract RGB values
                        val originalRed = (originalPixel shr 16) and 0xFF
                        val originalGreen = (originalPixel shr 8) and 0xFF
                        val originalBlue = originalPixel and 0xFF

                        val compressedRed = (compressedPixel shr 16) and 0xFF
                        val compressedGreen = (compressedPixel shr 8) and 0xFF
                        val compressedBlue = compressedPixel and 0xFF

                        // Calculate squared differences
                        localMSE += (originalRed - compressedRed) * (originalRed - compressedRed)
                        localMSE += (originalGreen - compressedGreen) * (originalGreen - compressedGreen)
                        localMSE += (originalBlue - compressedBlue) * (originalBlue - compressedBlue)
                    }
                    mse.addAndGet(localMSE)
                }
            }
        }

        return mse.get().toFloat() / (width * height * 3)
    }
}