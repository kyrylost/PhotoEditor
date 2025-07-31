package dev.stukalo.presentation.navigation.processing.flow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.stukalo.presentation.core.ui.components.ErrorWithRetry
import dev.stukalo.presentation.core.ui.components.Loader
import dev.stukalo.presentation.core.navigation.core.ext.safeNavigation
import dev.stukalo.presentation.navigation.processing.flow.navigation.inner.InnerProcessingDirection
import dev.stukalo.presentation.navigation.processing.flow.navigation.inner.InnerProcessingNavigationGraph
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ProcessingFlowScreen(
    modifier: Modifier = Modifier,
    imageUrl: String,
    appNavController: NavHostController,
) {

    val navController = rememberNavController()
    val viewModel: ProcessingFlowViewModel = koinViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.consumeIntent(
            ProcessingFlowViewIntent.GetBitmapFromUrl(
                originalImageUrl = imageUrl
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.singleEvent.collectLatest {
            when(it) {
                is ProcessingFlowSingleEvent.NavigateToCompare ->
                    navController.safeNavigation(InnerProcessingDirection.Compare)

                is ProcessingFlowSingleEvent.NavigateToLogs -> {
                    with(it) {
                        navController.safeNavigation(
                            InnerProcessingDirection.Logs(
                                processedImageBytes = processedImageBytes,
                                usedAlgorithm = usedAlgorithm,
                                usedAsyncMethod = usedAsyncMethod,
                                compressionTime = compressionTime,
                                compressionLevel = compressionLevel,
                            )
                        )
                    }
                }
            }
        }
    }

    state.apply {
        if (isLoading) {
            Loader(modifier = Modifier.fillMaxSize())
        } else if (isError || originalImageBitmap == null) {
            ErrorWithRetry(modifier = Modifier.fillMaxSize()) {
                viewModel.consumeIntent(
                    ProcessingFlowViewIntent.GetBitmapFromUrl(
                        originalImageUrl = imageUrl
                    )
                )
            }
        } else {
            Box(modifier) {
                InnerProcessingNavigationGraph(
                    appNavController = appNavController,
                    navController = navController,
                    originalImageBitmap = originalImageBitmap,
                    processedImageBitmap = processedImageBitmap,
                    onEvent = viewModel::consumeIntent,
                )
            }
        }
    }
}
