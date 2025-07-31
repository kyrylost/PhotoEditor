package dev.stukalo.presentation.feature.logs.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.stukalo.presentation.core.ui.components.Text
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.ui.theme.LocalPEElevation
import dev.stukalo.presentation.core.ui.theme.LocalPEShape
import dev.stukalo.presentation.core.ui.theme.LocalPETypography
import dev.stukalo.presentation.core.localization.R
import dev.stukalo.presentation.feature.logs.model.ChartType
import dev.stukalo.presentation.core.ui.R.drawable as RDrawable


@Composable
internal fun ChartTypeDropdownMenu(
    modifier: Modifier = Modifier,
    isDropDownExpanded: MutableState<Boolean>,
    selectedChartType: ChartType,
    onChangeChartType: (ChartType) -> Unit = {},
) {
    DropdownMenu(
        modifier = modifier,
        expanded = isDropDownExpanded.value,
        shadowElevation = LocalPEElevation.current.normal,
        shape = LocalPEShape.current.normalRoundedCornerShape,
        containerColor = LocalPEColors.current.background,
        onDismissRequest = {
            isDropDownExpanded.value = false
        }
    ) {
        ChartType.entries.forEach { chartType ->
            DropdownMenuItem(
                trailingIcon = {
                    if (selectedChartType == chartType) {
                        Image(
                            modifier = Modifier.size(12.dp),
                            painter = painterResource(RDrawable.ic_check_mark),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(color = LocalPEColors.current.primary)
                        )
                    }
                },
                text = {
                    Text(
                        style = LocalPETypography.current.bodyNormal,
                        text = when(chartType) {
                            ChartType.TIME -> stringResource(R.string.logs_chart_compression_time)
                            ChartType.MSE -> stringResource(R.string.logs_chart_mse)
                            ChartType.CR -> stringResource(R.string.logs_chart_cr)
                        }
                    )
                },
                onClick = {
                    isDropDownExpanded.value = false
                    onChangeChartType(chartType)
                }
            )
        }
    }
}

@ThemedPreview
@Composable
private fun PreviewSortOrderDropdownMenu() {
    Preview {
//        SortOrderDropdownMenu()
    }
}