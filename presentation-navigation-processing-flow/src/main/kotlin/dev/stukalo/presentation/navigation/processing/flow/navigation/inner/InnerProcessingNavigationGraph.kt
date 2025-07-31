package dev.stukalo.presentation.navigation.processing.flow.navigation.inner

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.stukalo.presentation.feature.compare.navigation.CompareTab
import dev.stukalo.presentation.feature.compare.navigation.CompareScreenAction
import dev.stukalo.presentation.feature.compressor.navigation.CompressorTab
import dev.stukalo.presentation.feature.compressor.navigation.CompressorScreenAction
import dev.stukalo.presentation.feature.logs.navigation.LogsTab
import dev.stukalo.presentation.feature.logs.navigation.LogsScreenAction
import dev.stukalo.presentation.navigation.processing.flow.ProcessingFlowViewIntent

@Composable
internal fun InnerProcessingNavigationGraph(
    appNavController: NavHostController,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    originalImageBitmap: Bitmap,
    processedImageBitmap: Bitmap?,
    onEvent: (ProcessingFlowViewIntent) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = InnerProcessingDirection.Compressor,
        modifier = modifier,
    ) {

        composable<InnerProcessingDirection.Compressor> {
            CompressorTab(
                originalImageBitmap = originalImageBitmap,
                onAction = {
                    when (it) {
                        is CompressorScreenAction.GoBack ->
                            appNavController.popBackStack()

                        is CompressorScreenAction.GoToCompare ->
                            onEvent(
                                ProcessingFlowViewIntent.OnNavigateToCompare(
                                    it.processedImage
                                )
                            )

                        is CompressorScreenAction.GoToLogs ->
                            onEvent(
                                ProcessingFlowViewIntent.OnNavigateToLogs(
                                    processedImageBitmap = it.processedImage,
                                    processedImageBytes = it.processedImageBytes,
                                    usedAlgorithm = it.usedAlgorithm,
                                    usedAsyncMethod = it.usedAsyncMethod,
                                    compressionTime = it.compressionTime,
                                    compressionLevel = it.compressionLevel
                                )
                            )
                        }

                },
            )
        }
        composable<InnerProcessingDirection.Compare> {
            CompareTab(
                originalImageBitmap = originalImageBitmap,
                processedImageBitmap = processedImageBitmap,
                onAction = {
                    when (it) {
                        is CompareScreenAction.GoBack ->
                            navController.popBackStack()
                    }
                }
            )
        }

        composable<InnerProcessingDirection.Logs> {
            val args = it.toRoute<InnerProcessingDirection.Logs>()

            with(args) {
                LogsTab(
                    processedImageBytes = processedImageBytes,
                    usedAlgorithm = usedAlgorithm,
                    usedAsyncMethod = usedAsyncMethod,
                    compressionTime = compressionTime,
                    compressionLevel = compressionLevel,
                    originalImageBitmap = originalImageBitmap,
                    processedImageBitmap = processedImageBitmap,
                    onAction = {
                        when (it) {
                            is LogsScreenAction.GoBack ->
                                navController.popBackStack()
                        }
                    }
                )
            }
        }
    }
}
