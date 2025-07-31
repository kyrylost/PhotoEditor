package dev.stukalo.presentation.core.ui.util.ext

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.shadowWithOffset(
    color: Color = dev.stukalo.presentation.core.ui.theme.LocalPEColors.current.shadow,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    clip: Dp = 0.dp
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = (
                    BlurMaskFilter(
                        blurRadius.toPx(),
                        BlurMaskFilter.Blur.NORMAL
                    )
                )
            }
            frameworkPaint.color = color.toArgb()

            val leftPixel = offsetX.toPx()
            val topPixel = offsetY.toPx()
            val rightPixel = size.width + topPixel
            val bottomPixel = size.height + leftPixel

            canvas.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = clip.toPx(),
                radiusY = clip.toPx(),
                paint = paint,
            )
        }
    }
)

@Composable
inline fun Modifier.debounceClickable(
    debounceInterval: Long = 400,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = LocalIndication.current,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    clickable(
        interactionSource = interactionSource,
        indication = indication,
    ) {
        val currentTime = System.currentTimeMillis()
        if ((currentTime - lastClickTime) < debounceInterval) return@clickable
        lastClickTime = currentTime
        onClick()
    }
}

inline fun Modifier.debounceClickableNoIndication(
    debounceInterval: Long = 400,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    clickable(
        interactionSource = interactionSource,
        indication = indication,
    ) {
        val currentTime = System.currentTimeMillis()
        if ((currentTime - lastClickTime) < debounceInterval) return@clickable
        lastClickTime = currentTime
        onClick()
    }
}
