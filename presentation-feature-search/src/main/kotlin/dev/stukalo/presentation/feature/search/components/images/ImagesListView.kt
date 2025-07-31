package dev.stukalo.presentation.feature.search.components.images

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.stukalo.presentation.feature.search.core.model.SearchResultUiModel
import dev.stukalo.presentation.core.ui.components.ErrorWithRetry
import dev.stukalo.presentation.core.ui.components.Loader
import dev.stukalo.presentation.core.ui.components.PESpacer
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.util.ext.shadowWithOffset
import dev.stukalo.presentation.feature.search.SearchScreenViewIntent
import dev.stukalo.presentation.feature.search.core.model.GridType
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun ImagesListView(
    modifier: Modifier = Modifier,
    searchPagingItems: LazyPagingItems<SearchResultUiModel>,
    gridType: GridType,
    onEvent: (SearchScreenViewIntent) -> Unit = {},
) {

    val gridScrollState = rememberLazyStaggeredGridState()

    LaunchedEffect(gridScrollState) {
        snapshotFlow { gridScrollState.firstVisibleItemIndex }
            .collect { index ->
                onEvent(SearchScreenViewIntent.OnScroll(index))
            }
    }

    LazyVerticalStaggeredGrid(
        modifier = modifier,
        state = gridScrollState,
        columns = StaggeredGridCells.Fixed(
            when (gridType) {
                GridType.GRID -> 2
                GridType.COLUMN -> 1
            }
        ),
        contentPadding = PaddingValues(24.dp),
        verticalItemSpacing = 24.dp,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(
            count = searchPagingItems.itemCount,
            key = { it },
        ) { index ->
            searchPagingItems[index]?.apply {
                ImagesItemView(
                    modifier = Modifier.shadowWithOffset(
                        blurRadius = 16.dp,
                        clip = 24.dp,
                        offsetY = 8.dp
                    ),
                    width = width,
                    height = height,
                    imageUrl = url,
                    onImageClick = onEvent,
                )
            }
        }

        item(span = StaggeredGridItemSpan.FullLine) {
            searchPagingItems.apply {
                Column {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            Loader(modifier = Modifier.fillMaxSize())
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = loadState.refresh as LoadState.Error

                            ErrorWithRetry(
                                modifier = Modifier.fillMaxSize(),
                                message = error.error.localizedMessage
                                    ?: error.error.message.orEmpty()
                            )
                        }

                        loadState.append is LoadState.Loading -> {
                            Loader(modifier = Modifier.fillMaxWidth())
                        }

                        loadState.append is LoadState.Error -> {
                            val error = loadState.append as LoadState.Error
                            ErrorWithRetry(
                                modifier = Modifier.fillMaxWidth(),
                                message = error.error.localizedMessage
                                    ?: error.error.message.orEmpty()
                            )
                        }
                    }

                    PESpacer(48.dp)
                }
            }
        }
    }
}

@ThemedPreview
@Composable
private fun PreviewSearchItemsList() {
    Preview(showBackground = true) {
        ImagesListView(
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
            ).collectAsLazyPagingItems(),
            gridType = GridType.GRID,
        )
    }
}