package dev.stukalo.presentation.feature.logs.components.chart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.stukalo.presentation.core.ui.R
import dev.stukalo.presentation.core.ui.components.Loader
import dev.stukalo.presentation.core.ui.theme.LocalPETypography
import dev.stukalo.presentation.feature.logs.LogsScreenScreenState
import dev.stukalo.presentation.feature.logs.LogsScreenViewIntent
import dev.stukalo.presentation.feature.logs.components.dialog.ChartTypeDropdownMenu
import dev.stukalo.presentation.feature.logs.model.ChartType

@Composable
internal fun ChartLayout(
    state: LogsScreenScreenState,
    onEvent: (LogsScreenViewIntent) -> Unit,
) {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    with(state) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                Loader(
                    modifier = Modifier
                        .fillMaxSize()
                )
            } else {
                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .clickable(
                            interactionSource = null,
                            indication = null,
                        ) {
                            isDropDownExpanded.value = true
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        style = LocalPETypography.current.titleNormal,
                        text = state.selectedChartType.name
                    )

                    Image(
                        painter = painterResource(R.drawable.ic_arrow_down),
                        contentDescription = null
                    )
                }

                ChartTypeDropdownMenu(
                    isDropDownExpanded = isDropDownExpanded,
                    selectedChartType = state.selectedChartType,
                    onChangeChartType = {
                        onEvent(LogsScreenViewIntent.OnSelectChartType(it))
                    }
                )

                when(selectedChartType) {
                    ChartType.TIME -> TimeChartLayout(
                        nonAsyncCompressionTime = nonAsyncCompressionTime.toDouble(),
                        asyncTaskCompressionTime = asyncTaskCompressionTime.toDouble(),
                        coroutinesCompressionTime = coroutinesCompressionTime.toDouble(),
                        javaCompressionTime = javaCompressionTime.toDouble(),
                    )
                    ChartType.MSE -> MSEChartLayout(
                        rleMSE = rleMSE.toDouble(),
                        downsamplingMSE = downsamplingMSE.toDouble(),
                        downsamplingRleMSE = downsamplingRleMSE.toDouble()
                    )
                    ChartType.CR -> CRChartLayout(
                        rleCR = rleCR.toDouble(),
                        downsamplingCR = downsamplingCR.toDouble(),
                        downsamplingRleCR = downsamplingRleCR.toDouble()
                    )
                }
            }
        }
    }
}