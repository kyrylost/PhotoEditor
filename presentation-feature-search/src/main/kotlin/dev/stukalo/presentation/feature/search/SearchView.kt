package dev.stukalo.presentation.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.PagingData
import dev.stukalo.presentation.feature.search.core.model.SearchResultUiModel
import dev.stukalo.presentation.core.ui.components.Loader
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.feature.search.components.category.CategoriesView
import dev.stukalo.presentation.feature.search.components.images.ImagesView
import dev.stukalo.presentation.feature.search.core.model.CategoryUiModel
import dev.stukalo.presentation.feature.search.core.model.GridType
import dev.stukalo.presentation.feature.search.core.model.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
internal fun SearchView(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean,
    searchPagingItems: Flow<PagingData<SearchResultUiModel>>,
    categories: List<CategoryUiModel>,
    categoriesIsHidden: Boolean,
    selectedSortOrder: SortOrder,
    gridType: GridType,
    onIntent: (SearchScreenViewIntent) -> Unit = {},
) {
    Column (modifier = modifier) {
        if (isLoading) {
            Loader(modifier = Modifier.fillMaxSize())
        } else if (isError) {
            //
        } else {
            CategoriesView(
                categoriesIsHidden = categoriesIsHidden,
                categories = categories,
                onCategoryClick = onIntent
            )

            ImagesView(
                selectedSortOrder = selectedSortOrder,
                searchPagingItems = searchPagingItems,
                gridType = gridType,
                onEvent = onIntent,
            )
        }
    }
}

@ThemedPreview
@Composable
private fun SearchViewPreview() {
    Preview(showBackground = true) {
        SearchView(
            isLoading = false,
            isError = false,
            searchPagingItems = emptyFlow(),
            categories = emptyList(),
            categoriesIsHidden = false,
            selectedSortOrder = SortOrder.LATEST,
            gridType = GridType.GRID,
        )
    }
}
