package dev.stukalo.presentation.core.ui.model

import androidx.annotation.Keep

@Keep
enum class Algorithm(
    val title: String
) {
    None("Not selected"),
    RunLengthEncoding("RLE"),
    Downsampling("DS"),
    DownsamplingRLE("RLE + DS"),
}
