package dev.stukalo.presentation.navigation.main.flow.bottomnav

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import dev.stukalo.presentation.core.ui.theme.LocalPEColors

@Composable
fun RowScope.NavigationItem(
    navigationItem: NavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                if (!isSelected) onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(navigationItem.icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = if (isSelected) LocalPEColors.current.primary else LocalPEColors.current.icon
            )
        )
    }
}