package dev.stukalo.presentation.feature.compressor

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.stukalo.presentation.core.ui.components.ErrorWithRetry
import dev.stukalo.presentation.core.ui.components.Loader
import dev.stukalo.presentation.core.ui.components.snackbar.PESnackbarHost
import dev.stukalo.presentation.core.ui.platform.OnActionHandler
import dev.stukalo.presentation.feature.compressor.navigation.CompressorScreenAction
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CompressorScreen(
    modifier: Modifier = Modifier,
    originalImageBitmap: Bitmap,
    onAction: OnActionHandler = {},
) {

    val viewModel: CompressorViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.consumeIntent(
            CompressorScreenViewIntent.OnLaunch(
                originalImageBitmap
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.singleEvent.collectLatest { singleEvent ->
            when(singleEvent) {
                is CompressorScreenSingleEvent.Error -> {
                    snackbarHostState.showSnackbar(
                        message = singleEvent.message,
                    )
                }

                CompressorScreenSingleEvent.NavigateBack ->
                    onAction(CompressorScreenAction.GoBack)

                is CompressorScreenSingleEvent.NavigateToCompareClick ->
                    onAction(
                        CompressorScreenAction.GoToCompare(
                            singleEvent.processedImage
                        )
                    )


                is CompressorScreenSingleEvent.NavigateToLogsClick ->
                    with(singleEvent) {
                        onAction(
                            CompressorScreenAction.GoToLogs(
                                processedImage,
                                processedImageBytes,
                                usedAlgorithm,
                                usedAsyncMethod,
                                compressionTime,
                                compressionLevel,
                            )
                        )
                    }

            }
        }
    }

    Box(modifier = modifier.fillMaxSize().navigationBarsPadding()) {
        with(state) {
            if(isLoading) {
                Loader(modifier = Modifier.fillMaxSize())
            } else if(isError) {
                ErrorWithRetry(modifier = Modifier.fillMaxSize())
            } else {
                println(processedImage)
                processedImage?.let {
                    CompressorView(
                        modifier = Modifier,
                        processedImage = processedImage,
                        isImageProcessing = isImageProcessing,
                        contentScale = contentScale,
                        compressionLevel = compressionLevel,
                        isCompressionViewVisible = isCompressionViewVisible,
                        isCompressionSliderVisible = isCompressionSliderVisible,
                        isAlgorithmPagerVisible = isAlgorithmPagerVisible,
                        isAsyncViewVisible = isAsyncViewVisible,
                        isAsyncPagerVisible = isAsyncPagerVisible,
                        algorithmPagerList = algorithmPagerList,
                        asyncMethodPagerList = asyncMethodPagerList,
                        onIntent = viewModel::consumeIntent,
                    )
                }
            }
        }
    }

    PESnackbarHost(
        snackbarState = snackbarHostState,
    )
}
