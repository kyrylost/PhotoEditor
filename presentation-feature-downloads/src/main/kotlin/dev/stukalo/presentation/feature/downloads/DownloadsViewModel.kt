package dev.stukalo.presentation.feature.downloads

import dev.stukalo.presentation.core.ui.platform.BaseViewModel
import dev.stukalo.domain.usecase.android.filestore.RetrieveImagesFromInternalStorageUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class DownloadsViewModel(
    private val retrieveImagesFromInternalStorageUseCase: RetrieveImagesFromInternalStorageUseCase,
) : BaseViewModel<DownloadsScreenScreenState, DownloadsScreenViewIntent, DownloadsScreenSingleEvent>() {

    override val _state: MutableStateFlow<DownloadsScreenScreenState> =
        MutableStateFlow(DownloadsScreenScreenState.initial())
    override val state: StateFlow<DownloadsScreenScreenState> = _state.asStateFlow()

    override val _singleEvent: MutableSharedFlow<DownloadsScreenSingleEvent> = MutableSharedFlow()
    override val singleEvent: SharedFlow<DownloadsScreenSingleEvent> = _singleEvent.asSharedFlow()

    override fun consumeIntent(intent: DownloadsScreenViewIntent) {
        launch {
            when (intent) {
                DownloadsScreenViewIntent.OnLoadDownloads -> {
                    loadImagesFromStorage()
                }
            }
        }
    }

    private suspend fun loadImagesFromStorage() {
        _state.update {
            it.copy(
                isLoading = true,
                isError = false,
            )
        }

        retrieveImagesFromInternalStorageUseCase()?.also {
            _state.update { state ->
                state.copy(
                    isLoading = false,
                    isError = false,
                    downloads = it
                )
            }
        } ?: {
            _state.update {
                it.copy(
                    isLoading = false,
                    isError = true,
                )
            }
        }
    }
}