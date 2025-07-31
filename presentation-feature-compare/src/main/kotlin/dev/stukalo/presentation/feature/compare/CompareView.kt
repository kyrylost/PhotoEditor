package dev.stukalo.presentation.feature.compare

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import dev.stukalo.presentation.core.ui.components.beforeafterslider.BeforeAfterSlider
import dev.stukalo.presentation.core.ui.components.beforeafterslider.BeforeAfterSliderDragPolicy
import dev.stukalo.presentation.core.ui.components.beforeafterslider.BeforeAfterSliderOrientation

@Composable
fun CompareView(
    modifier: Modifier = Modifier,
    originalImageBitmap: Bitmap,
    processedImageBitmap: Bitmap,
) {

    BeforeAfterSlider(
        modifier = modifier.fillMaxSize(),
        orientation = BeforeAfterSliderOrientation.VERTICAL,
        contentBefore = {
            Image(
                bitmap = originalImageBitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        },
        contentAfter = {
            Image(
                bitmap = processedImageBitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        },
        dragPolicy = BeforeAfterSliderDragPolicy.ALL,
    )

}