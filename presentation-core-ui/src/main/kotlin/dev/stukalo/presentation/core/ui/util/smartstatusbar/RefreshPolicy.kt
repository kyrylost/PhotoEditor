package dev.stukalo.presentation.core.ui.util.smartstatusbar

private const val DEFAULT_REFRESH_TIME = 500L
private const val DEFAULT_RECHECK_NUMBER = 6

sealed interface RefreshPolicy {
    data class OneTimeCheck(
        val waitBeforeCheck: Long = DEFAULT_REFRESH_TIME
    ): RefreshPolicy

    data class RefreshOnInteraction(
        val debounce: Long = DEFAULT_REFRESH_TIME,
        val recheck: Int = DEFAULT_RECHECK_NUMBER,
        val waitAfterCheck: Long = DEFAULT_REFRESH_TIME,
    ): RefreshPolicy

    data class RefreshContinuously(
        val waitAfterCheck: Long = DEFAULT_REFRESH_TIME
    ): RefreshPolicy
}