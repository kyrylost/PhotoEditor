package dev.stukalo.presentation.feature.downloads.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.stukalo.presentation.core.ui.components.Text
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.theme.LocalPETypography

@Composable
fun EmptyDownloadsView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "There is nothing to show",
            style = LocalPETypography.current.titleNormal
        )
    }
}

@ThemedPreview
@Composable
private fun PreviewEmptyDownloadsView() {
    Preview(showBackground = true) {
        EmptyDownloadsView()
    }
}