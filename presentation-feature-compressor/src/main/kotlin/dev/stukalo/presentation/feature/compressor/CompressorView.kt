package dev.stukalo.presentation.feature.compressor

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.model.AsyncMethod
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.feature.compressor.components.AlgorithmView
import dev.stukalo.presentation.feature.compressor.components.AsyncTechniqueView
import dev.stukalo.presentation.feature.compressor.components.CompressionView
import dev.stukalo.presentation.feature.compressor.components.CroppedImageView
import dev.stukalo.presentation.feature.compressor.components.FittedImageView

@Composable
internal fun CompressorView(
    modifier: Modifier = Modifier,
    processedImage: Bitmap,
    isImageProcessing: Boolean,
    contentScale: ContentScale,
    compressionLevel: Int,
    isCompressionViewVisible: Boolean,
    isCompressionSliderVisible: Boolean,
    isAlgorithmPagerVisible: Boolean,
    isAsyncViewVisible: Boolean,
    isAsyncPagerVisible: Boolean,
    algorithmPagerList: List<Algorithm>,
    asyncMethodPagerList: List<AsyncMethod>,
    onIntent: (CompressorScreenViewIntent) -> Unit = {},
) {
    Column(modifier = modifier) {
        if (contentScale == ContentScale.Fit) {
            FittedImageView(
                modifier = Modifier.weight(1F),
                processedImage = processedImage,
                isImageProcessing = isImageProcessing,
                onIntent = onIntent,
            )
        } else {
            CroppedImageView(
                modifier = Modifier.weight(1F),
                processedImage = processedImage,
                isImageProcessing = isImageProcessing,
                onIntent = onIntent,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CompressionView(
                isViewVisible = isCompressionViewVisible,
                isCompressionSliderVisible = isCompressionSliderVisible,
                compressionLevel = compressionLevel,
                onEvent = onIntent,
            )

            AlgorithmView(
                isAlgorithmPager = isAlgorithmPagerVisible,
                algorithms = algorithmPagerList,
                onEvent = onIntent,
            )

            AsyncTechniqueView(
                isViewVisible = isAsyncViewVisible,
                isAsyncTechniquePagerVisible = isAsyncPagerVisible,
                asyncMethods = asyncMethodPagerList,
                onEvent = onIntent,
            )
        }
    }
}

@ThemedPreview
@Composable
private fun CompressorViewPreview() {
    Preview(
        showBackground = true,
        fillMaxSize = true,
    ) {
        CompressorView(
            processedImage = createBitmap(100, 100),
            isImageProcessing = false,
            contentScale = ContentScale.Crop,
            compressionLevel = 50,
            isCompressionViewVisible = true,
            isCompressionSliderVisible = true,
            isAlgorithmPagerVisible = true,
            isAsyncViewVisible = true,
            isAsyncPagerVisible = true,
            algorithmPagerList = listOf(
                Algorithm.RunLengthEncoding,
                Algorithm.DownsamplingRLE,
                Algorithm.Downsampling
            ),
            asyncMethodPagerList = listOf(
                AsyncMethod.Coroutines,
                AsyncMethod.AsyncTask,
                AsyncMethod.Java,
                AsyncMethod.NonAsyncRun
            ),
        )
    }
}