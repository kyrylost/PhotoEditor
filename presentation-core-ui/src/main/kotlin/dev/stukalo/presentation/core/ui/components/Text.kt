package dev.stukalo.presentation.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.theme.LocalPETypography

@Composable
fun Text(
    modifier: Modifier = Modifier,
    style: TextStyle,
    text: String
) {
    Text(
        modifier = modifier,
        style = style,
        text = text,
    )
}

@Composable
@ThemedPreview
fun TextPreview() {
    Preview(
        showBackground = true,
    ) {
        Text(
            style = LocalPETypography.current.titleLarge,
            text = "Hello world",
        )
    }
}