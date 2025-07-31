package dev.stukalo.presentation.navigation.processing.flow

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.model.AsyncMethod
import dev.stukalo.presentation.core.ui.platform.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL


internal class ProcessingFlowViewModel: BaseViewModel<ProcessingFlowUiState, ProcessingFlowViewIntent, ProcessingFlowSingleEvent>() {
    override val _state: MutableStateFlow<ProcessingFlowUiState> =
        MutableStateFlow(ProcessingFlowUiState.initial())
    override val state: StateFlow<ProcessingFlowUiState> = _state.asStateFlow()

    override val _singleEvent: MutableSharedFlow<ProcessingFlowSingleEvent> = MutableSharedFlow()
    override val singleEvent: SharedFlow<ProcessingFlowSingleEvent> = _singleEvent.asSharedFlow()

    override fun consumeIntent(intent: ProcessingFlowViewIntent) {
        launch(
            coroutineContext = Dispatchers.Default
        ) {
            when(intent) {
                is ProcessingFlowViewIntent.GetBitmapFromUrl -> setImageBitmapFromUrl(intent.originalImageUrl)

                is ProcessingFlowViewIntent.OnNavigateToCompare -> navigateToCompare(intent.processedImageBitmap)

                is ProcessingFlowViewIntent.OnNavigateToLogs -> {
                    with(intent) {
                        navigateToLogs(
                            processedImageBitmap = processedImageBitmap,
                            processedImageBytes = processedImageBytes,
                            usedAlgorithm = usedAlgorithm,
                            usedAsyncMethod = usedAsyncMethod,
                            compressionTime = compressionTime,
                            compressionLevel = compressionLevel,
                        )
                    }
                }
            }
        }
    }

    private suspend fun setImageBitmapFromUrl(originalImageUrl: String) {
        try {
            withContext(Dispatchers.IO) {
                val url = URL(originalImageUrl)
                val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())

                _state.update {
                    it.copy(
                        isLoading = false,
                        originalImageBitmap = image
                    )
                }
            }
        } catch (e: IOException) {
            _state.update {
                it.copy(
                    isLoading = false,
                    isError = true,
                )
            }
        }
    }

    private fun setImageBitmap(image: Bitmap) = launch(Dispatchers.Default) {
        _state.update {
            it.copy(
                processedImageBitmap = image,
            )
        }
    }

    private fun navigateToCompare(processedImageBitmap: Bitmap) {
        launch(Dispatchers.Default) {
            setImageBitmap(processedImageBitmap).join()
            emitSingleEvent(ProcessingFlowSingleEvent.NavigateToCompare)
        }
    }

    private fun navigateToLogs(
        processedImageBitmap: Bitmap,
        processedImageBytes: Int,
        usedAlgorithm: Algorithm,
        usedAsyncMethod: AsyncMethod,
        compressionTime: Int,
        compressionLevel: Int,
    ) {
        launch(Dispatchers.Default) {
            setImageBitmap(processedImageBitmap).join()
            emitSingleEvent(
                ProcessingFlowSingleEvent.NavigateToLogs(
                    processedImageBytes = processedImageBytes,
                    usedAlgorithm = usedAlgorithm,
                    usedAsyncMethod = usedAsyncMethod,
                    compressionTime = compressionTime,
                    compressionLevel = compressionLevel,
                )
            )
        }
    }
}