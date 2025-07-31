package dev.stukalo.presentation.feature.search.components.dialog

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
import dev.stukalo.presentation.feature.search.R
import dev.stukalo.presentation.feature.search.SearchScreenViewIntent
import dev.stukalo.presentation.feature.search.core.model.SortOrder
import dev.stukalo.presentation.core.localization.R.string as RString

@Composable
internal fun SortOrderDropdownMenu(
    modifier: Modifier = Modifier,
    isDropDownExpanded: MutableState<Boolean>,
    selectedSortOrder: SortOrder,
    onChangeSortOrder: (SearchScreenViewIntent) -> Unit = {},
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
        SortOrder.entries.forEach { sortOrder ->
            DropdownMenuItem(
                trailingIcon = {
                    if (selectedSortOrder == sortOrder) {
                        Image(
                            modifier = Modifier.size(12.dp),
                            painter = painterResource(R.drawable.ic_check_mark),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(color = LocalPEColors.current.primary)
                        )
                    }
                },
                text = {
                    Text(
                        style = LocalPETypography.current.bodyNormal,
                        text = when(sortOrder) {
                            SortOrder.LATEST -> stringResource(RString.search_recently_added)
                            SortOrder.RELEVANT -> stringResource(RString.search_relevant)
                        }
                    )
                },
                onClick = {
                    isDropDownExpanded.value = false
                    onChangeSortOrder(SearchScreenViewIntent.OnChangeSortOrder(sortOrder))
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