package dev.stukalo.presentation.core.ui.model

import androidx.annotation.Keep

@Keep
enum class AsyncMethod {
    Coroutines,
    AsyncTask,
    Java,
    NonAsyncRun,
}
