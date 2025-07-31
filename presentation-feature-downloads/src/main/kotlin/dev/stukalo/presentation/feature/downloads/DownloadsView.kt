package dev.stukalo.presentation.feature.downloads

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.stukalo.presentation.feature.downloads.components.EmptyDownloadsView
import dev.stukalo.presentation.feature.downloads.components.ZoomableImageGrid

@Composable
internal fun DownloadsView(
    modifier: Modifier = Modifier,
    downloads: List<Bitmap>,
) {

    Box(modifier = modifier.fillMaxSize()) {
        if (downloads.isEmpty()) {
            EmptyDownloadsView()
        } else {
            ZoomableImageGrid(downloads)
        }
    }
}