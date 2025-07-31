package dev.stukalo.presentation.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.ui.theme.LocalPETypography
import dev.stukalo.presentation.core.ui.util.ext.debounceClickableNoIndication

@Composable
fun Button(
    text: String,
    backgroundColor: Color,
    foregroundColor: Color = Color.White,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .debounceClickableNoIndication(
                onClick = onClick
            )
            .padding(16.dp)
    ) {
        Text(
            style = LocalPETypography.current.titleSmall.copy(
                color = foregroundColor
            ),
            text = text
        )
    }
}

@Composable
@ThemedPreview
fun ButtonPreview() {
    Preview {
        Button(
            backgroundColor = LocalPEColors.current.primary,
            text = "Hello world",
        )
    }
}