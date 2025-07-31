package dev.stukalo.presentation.feature.search.components.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import dev.stukalo.presentation.feature.search.core.model.SearchResultUiModel
import dev.stukalo.presentation.core.ui.components.PESpacer
import dev.stukalo.presentation.core.ui.components.Text
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.ui.theme.LocalPETypography
import dev.stukalo.presentation.feature.search.R
import dev.stukalo.presentation.feature.search.SearchScreenViewIntent
import dev.stukalo.presentation.feature.search.components.dialog.SortOrderDropdownMenu
import dev.stukalo.presentation.feature.search.core.model.GridType
import dev.stukalo.presentation.feature.search.core.model.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import dev.stukalo.presentation.core.ui.R.drawable as RDrawable
import dev.stukalo.presentation.core.localization.R.string as RString

@Composable
internal fun ImagesView(
    modifier: Modifier = Modifier,
    searchPagingItems: Flow<PagingData<SearchResultUiModel>>,
    selectedSortOrder: SortOrder,
    gridType: GridType,
    onEvent: (SearchScreenViewIntent) -> Unit = {},
) {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.clickable(
                    interactionSource = null,
                    indication = null,
                ) {
                    isDropDownExpanded.value = true
                },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    style = LocalPETypography.current.titleNormal,
                    text = when(selectedSortOrder) {
                        SortOrder.LATEST -> stringResource(RString.search_recently_added)
                        SortOrder.RELEVANT -> stringResource(RString.search_relevant)
                    }
                )

                Image(
                    painter = painterResource(RDrawable.ic_arrow_down),
                    contentDescription = null
                )
            }

            SortOrderDropdownMenu(
                isDropDownExpanded = isDropDownExpanded,
                selectedSortOrder = selectedSortOrder,
                onChangeSortOrder = onEvent
            )

            PESpacer(1f)

            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                    ) {
                        onEvent(SearchScreenViewIntent.OnChangeGridType)
                    },
                painter = painterResource(
                    if (gridType == GridType.GRID) R.drawable.ic_menu_grid
                    else R.drawable.ic_menu_column
                ),
                colorFilter = ColorFilter.tint(color = LocalPEColors.current.primary),
                contentDescription = ""
            )
        }

        ImagesListView(
            modifier = Modifier,
            searchPagingItems = searchPagingItems.collectAsLazyPagingItems(),
            gridType = gridType,
            onEvent = onEvent,
        )
    }
}

@ThemedPreview
@Composable
private fun PreviewImagesView() {
    Preview(showBackground = true) {
        ImagesView(
            searchPagingItems = flowOf(
                PagingData.from(
                    listOf(
                        SearchResultUiModel(
                            id = "0",
                            height = 0,
                            width = 0,
                            url = ""
                        )
                    )
                )
            ),
            selectedSortOrder = SortOrder.LATEST,
            gridType = GridType.GRID,
        )
    }
}