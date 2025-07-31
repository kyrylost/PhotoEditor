package dev.stukalo.presentation.feature.logs

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.stukalo.presentation.core.ui.R
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.model.AsyncMethod
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.ui.util.ext.debounceClickableNoIndication
import dev.stukalo.presentation.feature.logs.components.LogList
import dev.stukalo.presentation.feature.logs.components.chart.ChartLayout

@Composable
internal fun LogsContent(
    modifier: Modifier = Modifier,
    state: LogsScreenScreenState,
    processedImageBytes: Int,
    usedAlgorithm: Algorithm,
    usedAsyncMethod: AsyncMethod,
    compressionTime: Int,
    originalImageBitmap: Bitmap,
    onIntent: (LogsScreenViewIntent) -> Unit,
) {

    Column (
        modifier = modifier
            .statusBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .debounceClickableNoIndication {
                    onIntent(LogsScreenViewIntent.OnNavigateBack)
                },
            colorFilter = ColorFilter.tint(LocalPEColors.current.textTitle),
            painter = painterResource(R.drawable.ic_back),
            contentDescription = ""
        )

        LogList(
            modifier = Modifier.padding(top = 24.dp),
            processedImageBytes = processedImageBytes,
            usedAlgorithm = usedAlgorithm,
            usedAsyncMethod = usedAsyncMethod,
            compressionTime = compressionTime,
            originalImageBitmap = originalImageBitmap,
        )

        ChartLayout(
            state = state,
            onEvent = onIntent
        )

    }
}
