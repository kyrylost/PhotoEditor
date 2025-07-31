package dev.stukalo.presentation.feature.search

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import dev.stukalo.presentation.core.ui.platform.mvi.SingleEvent
import dev.stukalo.presentation.core.ui.platform.mvi.UiState
import dev.stukalo.presentation.core.ui.platform.mvi.ViewIntent
import dev.stukalo.presentation.feature.search.core.model.CategoryNameUi
import dev.stukalo.presentation.feature.search.core.model.CategoryUiModel
import dev.stukalo.presentation.feature.search.core.model.GridType
import dev.stukalo.presentation.feature.search.core.model.SearchResultUiModel
import dev.stukalo.presentation.feature.search.core.model.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@Immutable
internal data class SearchScreenUiState(
    override val isLoading: Boolean,
    override val isError: Boolean,
    val searchPagingItems: Flow<PagingData<SearchResultUiModel>>,
    val categories: List<CategoryUiModel>,
    val categoriesIsHidden: Boolean,
    val selectedSortOrder: SortOrder,
    val gridType: GridType,
    val firstVisibleItemIndex: Int,
    val firstVisibleItemOffset: Int,
) : UiState {

    companion object {
        fun initial() = SearchScreenUiState(
            isLoading = true,
            isError = false,
            searchPagingItems = emptyFlow(),
            categories = emptyList(),
            categoriesIsHidden = false,
            selectedSortOrder = SortOrder.RELEVANT,
            gridType = GridType.GRID,
            firstVisibleItemIndex = 0,
            firstVisibleItemOffset = 0,
        )
    }

}

@Immutable
internal sealed interface SearchScreenViewIntent :
    ViewIntent {
    data object OnChangeGridType : SearchScreenViewIntent
    data class OnCategoryClick(val category: CategoryNameUi) : SearchScreenViewIntent
    data class OnChangeSortOrder(val sortOrder: SortOrder) : SearchScreenViewIntent
    data class OnImageClick(val imageUrl: String) : SearchScreenViewIntent
    data class OnScroll(val newFirstVisibleItem: Int) : SearchScreenViewIntent
}

@Immutable
internal sealed interface SearchScreenSingleEvent : SingleEvent {
    data class NavigateToCompressor(val imageUrl: String) : SearchScreenSingleEvent
}