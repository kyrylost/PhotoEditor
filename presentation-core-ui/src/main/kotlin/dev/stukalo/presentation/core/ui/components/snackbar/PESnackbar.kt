package dev.stukalo.presentation.core.ui.components.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import dev.stukalo.presentation.core.ui.components.Text
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.ui.theme.LocalPETypography

@Composable
fun PESnackbar(
    message: String,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment.Vertical,
    onAction: (() -> Unit)? = null,
) {
    val cornerRadius = 6.dp
    Row(
        modifier
            .fillMaxWidth()
            .shadow(
                elevation = 24.dp,
                spotColor = LocalPEColors.current.shadow,
                shape = RoundedCornerShape(12.dp),
            )
            .clip(
                RoundedCornerShape(
                    topStart = cornerRadius,
                    topEnd = cornerRadius,
                    bottomEnd = cornerRadius,
                    bottomStart = cornerRadius,
                )
            )
            .background(LocalPEColors.current.surface)
            .padding(start = 20.dp, top = 16.dp, end = 9.dp, bottom = 16.dp),
        verticalAlignment = contentAlignment,
    ) {

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = message,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            style = LocalPETypography.current.titleSmall
        )

        Spacer(modifier = Modifier.width(4.dp))
    }
}