package dev.stukalo.presentation.core.ui.preview

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.ui.theme.PETheme

@Target(AnnotationTarget.FUNCTION)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
annotation class ThemedPreview

@Composable
fun Preview(
    showBackground: Boolean = false,
    fillMaxSize: Boolean = false,
    content: @Composable () -> Unit
) {
    PETheme {
        Box(
            modifier = Modifier
                .then(
                    if (showBackground) Modifier.background(LocalPEColors.current.background) else Modifier
                )
                .then(
                    if (fillMaxSize) Modifier.fillMaxSize() else Modifier
                )
        ) {
            content()
        }
    }
}