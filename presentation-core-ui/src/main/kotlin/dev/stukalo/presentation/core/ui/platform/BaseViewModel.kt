package dev.stukalo.presentation.core.ui.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.stukalo.presentation.core.ui.platform.mvi.SingleEvent
import dev.stukalo.presentation.core.ui.platform.mvi.UiState
import dev.stukalo.presentation.core.ui.platform.mvi.ViewIntent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<T : UiState, in E : ViewIntent, S: SingleEvent> : ViewModel() {
    protected abstract val _state: MutableStateFlow<T>
    abstract val state: StateFlow<T>

    protected abstract val _singleEvent: MutableSharedFlow<S>
    abstract val singleEvent: SharedFlow<S>

    abstract fun consumeIntent(intent: E)

    protected fun emitSingleEvent(singleEvent: S) {
        viewModelScope.launch { _singleEvent.emit(singleEvent) }
    }

    protected fun <T> launch(
        coroutineContext: CoroutineDispatcher = Dispatchers.IO,
        coroutineScope: CoroutineScope = viewModelScope,
        onLoading: (suspend (Boolean) -> Unit)? = null,
        onResult: (suspend (T?) -> Unit)? = null,
        onError: (suspend (Throwable) -> Unit)? = null,
        debounce: Long? = null,
        request: suspend CoroutineScope.() -> T?,
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            coroutineScope.launch {
                onError?.invoke(throwable)
                onLoading?.invoke(false)
            }
        }
        return coroutineScope.launch(
            context = exceptionHandler + coroutineContext,
        ) {
            debounce?.let { delay(it) }
            onLoading?.invoke(true)
            withContext(coroutineContext) { request() }.apply {
                this.let { result ->
                    onResult?.invoke(result)
                    onLoading?.invoke(false)
                }
            }
        }
    }
}