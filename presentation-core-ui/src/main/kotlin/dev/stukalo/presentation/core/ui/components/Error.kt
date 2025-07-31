package dev.stukalo.presentation.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import dev.stukalo.presentation.core.ui.R
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.theme.LocalPETypography

@ThemedPreview
@Composable
fun ErrorWithRetry(
    modifier: Modifier = Modifier,
    message: String = "",
    onRetry: (() -> Unit)? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            style = LocalPETypography.current.bodyNormal.copy(color = dev.stukalo.presentation.core.ui.theme.LocalPEColors.current.error),
            text = message
        )

        onRetry?.let {
            Image(
                painter = painterResource(R.drawable.ic_retry),
                colorFilter = ColorFilter.tint(color = dev.stukalo.presentation.core.ui.theme.LocalPEColors.current.icon),
                contentDescription = "",
            )
        }
    }
}