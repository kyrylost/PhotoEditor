package dev.stukalo.presentation.feature.logs.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.stukalo.presentation.core.ui.components.PESpacer
import dev.stukalo.presentation.core.ui.components.Text
import dev.stukalo.presentation.core.ui.theme.LocalPETypography

@Composable
internal fun LogListItem(
    modifier: Modifier = Modifier,
    logTitle: String,
    logValue: String,
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = logTitle,
            style = LocalPETypography.current.titleNormal
        )

        PESpacer(1f)

        Text(
            text = logValue,
            style = LocalPETypography.current.bodyNormal
        )
    }
}