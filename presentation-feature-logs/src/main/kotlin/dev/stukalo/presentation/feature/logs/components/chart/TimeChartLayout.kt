package dev.stukalo.presentation.feature.logs.components.chart

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.ui.theme.LocalPETypography
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties

@Composable
internal fun TimeChartLayout(
    nonAsyncCompressionTime: Double,
    asyncTaskCompressionTime: Double,
    coroutinesCompressionTime: Double,
    javaCompressionTime: Double
) {

    val chartBrush = Brush.verticalGradient(
        listOf(
            LocalPEColors.current.error,
            LocalPEColors.current.primary
        )
    )

    ColumnChart(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(top = 24.dp, bottom = 32.dp),
        labelHelperProperties = LabelHelperProperties(
            enabled = false
        ),
        labelProperties = LabelProperties(
            textStyle = LocalPETypography.current.bodyNormal,
            enabled = true
        ),
        indicatorProperties = HorizontalIndicatorProperties(
            textStyle = LocalPETypography.current.bodyNormal,
            enabled = true
        ),
        data = remember {
            listOf(
                Bars(
                    label = "Non async",
                    values = listOf(
                        Bars.Data(
                            value = nonAsyncCompressionTime,
                            color = chartBrush
                        ),
                    ),
                ),
                Bars(
                    label = "Async Task",
                    values = listOf(
                        Bars.Data(
                            value = asyncTaskCompressionTime,
                            color = chartBrush
                        ),
                    ),
                ),
                Bars(
                    label = "Coroutines",
                    values = listOf(
                        Bars.Data(
                            value = coroutinesCompressionTime,
                            color = chartBrush
                        ),
                    ),
                ),
                Bars(
                    label = "Java",
                    values = listOf(
                        Bars.Data(
                            value = javaCompressionTime,
                            color = chartBrush
                        )
                    ),
                ),
            )
        },
        barProperties = BarProperties(
            cornerRadius = Bars.Data.Radius.Rectangle(topRight = 8.dp, topLeft = 8.dp),
            thickness = 24.dp
        ),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
    )
}