package dev.stukalo.presentation.core.ui.components.snackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PESnackbarHost(
    snackbarState: SnackbarHostState,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment.Vertical = Alignment.CenterVertically,
) {
    SnackbarHost(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        hostState = snackbarState
    ) { snackbarData: SnackbarData ->
        PESnackbar(
            message = snackbarData.visuals.message,
            contentAlignment = contentAlignment
        )
    }
}