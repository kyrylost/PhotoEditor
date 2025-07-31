package dev.stukalo.presentation.feature.logs

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.ui.model.AsyncMethod
import dev.stukalo.presentation.core.ui.platform.OnActionHandler
import dev.stukalo.presentation.feature.logs.model.ChartType
import dev.stukalo.presentation.feature.logs.navigation.LogsScreenAction
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun LogsScreen(
    modifier: Modifier = Modifier,
    processedImageBytes: Int,
    usedAlgorithm: Algorithm,
    usedAsyncMethod: AsyncMethod,
    compressionTime: Int,
    compressionLevel: Int,
    originalImageBitmap: Bitmap,
    processedImageBitmap: Bitmap?,
    onAction: OnActionHandler = {},
) {

    processedImageBitmap?.let {

        val context = LocalActivity.current as ComponentActivity

        val viewModel: LogsViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.singleEvent.collectLatest { singleEvent ->
                when (singleEvent) {
                    is LogsScreenSingleEvent.NavigateBack -> onAction(LogsScreenAction.GoBack)

                    is LogsScreenSingleEvent.OnChartTypeSelected ->
                        viewModel.consumeIntent(
                            when (singleEvent.chartType) {
                                ChartType.TIME -> LogsScreenViewIntent.TimeChartTypeSelected(
                                    originalBitmap = originalImageBitmap,
                                    usedAlgorithm = usedAlgorithm,
                                    compressionLevel = compressionLevel
                                )

                                ChartType.MSE -> LogsScreenViewIntent.MSEChartTypeSelected(
                                    originalBitmap = originalImageBitmap,
                                    compressedBitmap = processedImageBitmap,
                                    usedAlgorithm = usedAlgorithm,
                                    compressionLevel = compressionLevel
                                )

                                ChartType.CR -> LogsScreenViewIntent.CRChartTypeSelected(
                                    originalBitmap = originalImageBitmap,
                                    compressedBitmapSize = processedImageBytes,
                                    usedAlgorithm = usedAlgorithm,
                                    compressionLevel = compressionLevel
                                )
                            }
                        )

                }
            }
        }

        LaunchedEffect(Unit) {
            context.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    Color.Transparent.toArgb(),
                    Color.Transparent.toArgb()
                )
            )
        }

        LaunchedEffect(Unit) {
            viewModel.consumeIntent(
                LogsScreenViewIntent.TimeChartTypeSelected(
                    originalBitmap = originalImageBitmap,
                    usedAlgorithm = usedAlgorithm,
                    compressionLevel = compressionLevel
                )
            )
        }

        LogsContent(
            modifier = modifier,
            state = state,
            processedImageBytes = processedImageBytes,
            usedAlgorithm = usedAlgorithm,
            usedAsyncMethod = usedAsyncMethod,
            compressionTime = compressionTime,
            originalImageBitmap = originalImageBitmap,
            onIntent = viewModel::consumeIntent,
        )
    }
}
