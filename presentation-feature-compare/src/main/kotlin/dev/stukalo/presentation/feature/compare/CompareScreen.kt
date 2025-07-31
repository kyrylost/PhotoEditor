package dev.stukalo.presentation.feature.compare

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.stukalo.presentation.core.ui.components.ErrorWithRetry
import dev.stukalo.presentation.core.ui.platform.OnActionHandler
import dev.stukalo.presentation.core.ui.util.ext.debounceClickable
import dev.stukalo.presentation.core.ui.util.smartstatusbar.InstallSmartStatusBar
import dev.stukalo.presentation.core.ui.util.smartstatusbar.RefreshPolicy
import dev.stukalo.presentation.feature.compare.navigation.CompareScreenAction
import dev.stukalo.presentation.core.localization.R

@Composable
fun CompareScreen(
    modifier: Modifier = Modifier,
    originalImageBitmap: Bitmap,
    processedImageBitmap: Bitmap?,
    onAction: OnActionHandler = {},
) {

    InstallSmartStatusBar(
        darkColorBound = 136,
        refreshPolicy = RefreshPolicy.OneTimeCheck(),
    )

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        processedImageBitmap?.let {
            CompareView(
                originalImageBitmap = originalImageBitmap,
                processedImageBitmap = processedImageBitmap,
            )
        } ?: ErrorWithRetry(
            message = stringResource(R.string.compare_error),
            modifier = Modifier
                .fillMaxSize()
                .debounceClickable(
                    onClick = {
                        onAction(CompareScreenAction.GoBack)
                    }
                ),
        )
    }
}