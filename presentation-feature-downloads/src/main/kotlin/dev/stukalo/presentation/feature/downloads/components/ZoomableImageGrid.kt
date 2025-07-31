package dev.stukalo.presentation.feature.downloads.components

import android.graphics.Bitmap
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceIn
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.stukalo.presentation.core.ui.components.ZoomableImage
import dev.stukalo.presentation.core.ui.util.ext.detectPinchGestures
import dev.stukalo.presentation.feature.downloads.model.GridConfig

@Composable
fun ZoomableImageGrid(imageList: List<Bitmap>) {

    var isDialogOpen by remember { mutableStateOf(false) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val gridConfigs = listOf(
        GridConfig(
            columns = 1,
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(0.dp),
            imageModifier = Modifier
        ),
        GridConfig(
            columns = 2,
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            contentPadding = PaddingValues(0.dp),
            imageModifier = Modifier.aspectRatio(1f)
        ),
        GridConfig(
            columns = 3,
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            contentPadding = PaddingValues(0.dp),
            imageModifier = Modifier.aspectRatio(1f)
        ),
        GridConfig(
            columns = 4,
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            contentPadding = PaddingValues(0.dp),
            imageModifier = Modifier.aspectRatio(1f)
        ),
        GridConfig(
            columns = 5,
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            contentPadding = PaddingValues(0.dp),
            imageModifier = Modifier.aspectRatio(1f)
        ),
        GridConfig(
            columns = 6,
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            contentPadding = PaddingValues(0.dp),
            imageModifier = Modifier.aspectRatio(1f)
        ),
        GridConfig(
            columns = 7,
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            contentPadding = PaddingValues(0.dp),
            imageModifier = Modifier.aspectRatio(1f)
        ),
        GridConfig(
            columns = 8,
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            contentPadding = PaddingValues(0.dp),
            imageModifier = Modifier.aspectRatio(1f)
        )
    )

    var level by remember { mutableIntStateOf(0) }
    var scale by remember { mutableFloatStateOf(1f) }

    val animatedScale by animateFloatAsState(
        targetValue = scale.fastCoerceIn(0.8f, 1.2f),
        animationSpec = spring(
            dampingRatio = DampingRatioLowBouncy,
            stiffness = StiffnessLow
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectPinchGestures(
                    pass = PointerEventPass.Initial,
                    onGesture = { _: Offset, zoomChange: Float ->
                        scale *= zoomChange

                        if (scale > 1.2f && level > 0) {
                            level--
                            scale = 1f
                        }

                        else if (scale < 0.8f && level < gridConfigs.lastIndex) {
                            level++
                            scale = 1f
                        }
                    },
                    onGestureEnd = {
                        scale = 1f
                    }
                )
            }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(gridConfigs[level].columns),
            verticalArrangement = gridConfigs[level].verticalArrangement,
            horizontalArrangement = gridConfigs[level].horizontalArrangement,
            contentPadding = gridConfigs[level].contentPadding,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                }
        ) {
            items(imageList) { bitmap ->
                ZoomableImageItem(
                    modifier = gridConfigs[level].imageModifier,
                    bitmap = bitmap,
                    onClick = {
                        selectedBitmap = it
                        isDialogOpen = true
                    }
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(72.dp))
            }
        }
    }

    if (isDialogOpen && selectedBitmap != null) {
        Dialog(
            onDismissRequest = { isDialogOpen = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = true
            )
        ) {
            ZoomableImage(selectedBitmap!!)
        }
    }
}