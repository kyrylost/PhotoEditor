package dev.stukalo.presentation.feature.downloads.components

import android.graphics.Bitmap
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import dev.stukalo.presentation.core.ui.preview.ThemedPreview

@Composable
fun LazyGridItemScope.ZoomableImageItem(
    modifier: Modifier = Modifier,
    bitmap: Bitmap,
    onClick: (Bitmap) -> Unit = {},
) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
            .animateItem(
                fadeInSpec = null, fadeOutSpec = null, placementSpec = spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntOffset.VisibilityThreshold
                )
            )
            .clickable {
                onClick(bitmap)
            }
    )
}

@ThemedPreview
@Composable
private fun PreviewZoomableImageItem() {
//    DownloadsListItem(
//
//    )
}