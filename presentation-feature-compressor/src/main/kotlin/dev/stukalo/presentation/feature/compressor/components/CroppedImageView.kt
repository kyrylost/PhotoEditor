package dev.stukalo.presentation.feature.compressor.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.stukalo.presentation.core.ui.components.Loader
import dev.stukalo.presentation.core.ui.components.PESpacer
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.util.ext.debounceClickable
import dev.stukalo.presentation.core.ui.util.smartstatusbar.InstallSmartStatusBar
import dev.stukalo.presentation.core.ui.util.smartstatusbar.RefreshPolicy
import dev.stukalo.presentation.feature.compressor.CompressorScreenViewIntent
import dev.stukalo.presentation.feature.compressor.R
import dev.stukalo.presentation.core.ui.R.drawable as RDrawable

@Composable
internal fun CroppedImageView(
    modifier: Modifier = Modifier,
    processedImage: Bitmap,
    isImageProcessing: Boolean,
    onIntent: (CompressorScreenViewIntent) -> Unit ={},
) {

    InstallSmartStatusBar(
        darkColorBound = 136,
        refreshPolicy = RefreshPolicy.OneTimeCheck(),
    )

    Box(
        modifier = modifier
    ) {

        if (isImageProcessing) {
            Loader(modifier = Modifier.fillMaxSize())
        } else {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = processedImage.asImageBitmap(),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }

        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(12.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .debounceClickable(
                        onClick = {
                            onIntent(CompressorScreenViewIntent.OnNavigateBackClick)
                        }
                    )
                    .padding(8.dp),
                painter = painterResource(RDrawable.ic_back),
                contentDescription = ""
            )

            PESpacer(1F)

            Column {
                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable {
                            onIntent(CompressorScreenViewIntent.OnNavigateToCompareClick)
                        }
                        .padding(8.dp),
                    painter = painterResource(R.drawable.ic_compare),
                    contentDescription = ""
                )

                Image(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable {
                            onIntent(CompressorScreenViewIntent.NavigateToLogsClick)
                        }
                        .padding(8.dp),
                    painter = painterResource(R.drawable.ic_logbook),
                    contentDescription = ""
                )

                Image(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable {
                            onIntent(CompressorScreenViewIntent.OnDownloadClick)
                        }
                        .padding(8.dp),
                    painter = painterResource(RDrawable.ic_download),
                    contentDescription = ""
                )

                Image(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable {
                            onIntent(CompressorScreenViewIntent.OnChangeContentScale)
                        }
                        .padding(8.dp),
                    painter = painterResource(R.drawable.ic_fit),
                    contentDescription = ""
                )
            }
        }
    }
}

@ThemedPreview
@Composable
private fun PreviewCroppedImageView() {
//    CroppedImageView()
}