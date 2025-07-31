package dev.stukalo.presentation.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.stukalo.presentation.core.ui.preview.ThemedPreview

@ThemedPreview
@Composable
fun Loader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = dev.stukalo.presentation.core.ui.theme.LocalPEColors.current.primary)
    }
}