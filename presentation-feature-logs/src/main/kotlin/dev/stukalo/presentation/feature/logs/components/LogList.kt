package dev.stukalo.presentation.feature.logs.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.model.AsyncMethod
import dev.stukalo.presentation.core.localization.R
import dev.stukalo.presentation.feature.logs.core.bytesToMegabytes

@Composable
internal fun LogList(
    modifier: Modifier = Modifier,
    processedImageBytes: Int,
    usedAlgorithm: Algorithm,
    usedAsyncMethod: AsyncMethod,
    compressionTime: Int,
    originalImageBitmap: Bitmap,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        LogListItem(
            logTitle = stringResource(R.string.logs_used_algorithm),
            logValue = usedAlgorithm.title,
        )

        LogListItem(
            logTitle = stringResource(R.string.logs_used_async_method),
            logValue = usedAsyncMethod.name,
        )

        LogListItem(
            logTitle = stringResource(R.string.logs_compression_time),
            logValue = compressionTime.toString(),
        )

        LogListItem(
            logTitle = stringResource(R.string.logs_original_image_size),
            logValue = originalImageBitmap.byteCount.bytesToMegabytes().toString(),
        )

        LogListItem(
            logTitle = stringResource(R.string.logs_compressed_image_size),
            logValue = processedImageBytes.bytesToMegabytes().toString(),
        )
    }
}