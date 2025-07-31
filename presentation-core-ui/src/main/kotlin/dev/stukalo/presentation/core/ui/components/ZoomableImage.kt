package dev.stukalo.presentation.core.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun ZoomableImage(bitmap: Bitmap, contentDescription: String? = null) {
    val angle by remember { mutableFloatStateOf(0f) }
    var zoom by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val window = LocalWindowInfo.current.containerSize
    val screenWidth = window.width.dp.value
    val screenHeight = window.height.dp.value

    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .graphicsLayer(
                scaleX = zoom,
                scaleY = zoom,
                rotationZ = angle
            )
            .pointerInput(Unit) {
                detectTransformGestures(
                    onGesture = { _, pan, gestureZoom, _ ->
                        zoom = (zoom * gestureZoom).coerceIn(1F..4F)
                        if (zoom > 1) {
                            val x = (pan.x * zoom)
                            val y = (pan.y * zoom)
                            val angleRad = angle * PI / 180.0

                            offsetX =
                                (offsetX + (x * cos(angleRad) - y * sin(angleRad)).toFloat()).coerceIn(
                                    -(screenWidth * zoom)..(screenWidth * zoom)
                                )
                            offsetY =
                                (offsetY + (x * sin(angleRad) + y * cos(angleRad)).toFloat()).coerceIn(
                                    -(screenHeight * zoom)..(screenHeight * zoom)
                                )
                        } else {
                            offsetX = 0F
                            offsetY = 0F
                        }
                    }
                )
            }
            .fillMaxSize()
    )
}