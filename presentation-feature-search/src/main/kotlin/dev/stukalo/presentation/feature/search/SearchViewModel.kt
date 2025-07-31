package dev.stukalo.presentation.feature.search

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dev.stukalo.presentation.core.ui.platform.BaseViewModel
import dev.stukalo.presentation.feature.search.core.mapper.CategoryDomainUiMapper
import dev.stukalo.presentation.feature.search.core.mapper.SearchDomainUiMapper
import dev.stukalo.presentation.feature.search.core.model.CategoryNameUi
import dev.stukalo.presentation.feature.search.core.model.GridType
import dev.stukalo.presentation.feature.search.core.model.SortOrder
import dev.stukalo.usecase.search.GetCategoriesUseCase
import dev.stukalo.usecase.search.SearchImagesByQueryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class SearchViewModel(
    private val searchImagesByQueryUseCase: SearchImagesByQueryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseViewModel<SearchScreenUiState, SearchScreenViewIntent, SearchScreenSingleEvent>() {

    override val _state: MutableStateFlow<SearchScreenUiState> = MutableStateFlow(
        SearchScreenUiState.initial()
    )
    override val state: StateFlow<SearchScreenUiState> = _state.asStateFlow()

    override val _singleEvent: MutableSharedFlow<SearchScreenSingleEvent> = MutableSharedFlow()
    override val singleEvent: SharedFlow<SearchScreenSingleEvent> = _singleEvent.asSharedFlow()

    private var lastScrollIndex = 0

    init {
        getCategories()
        searchImages()
    }

    override fun consumeIntent(intent: SearchScreenViewIntent) {
        launch(
            coroutineContext = Dispatchers.Default
        ) {
            when (intent) {
                SearchScreenViewIntent.OnChangeGridType -> {
                    _state.update {
                        it.copy(
                            gridType = if (it.gridType == GridType.GRID) GridType.COLUMN
                            else GridType.GRID
                        )
                    }
                }

                is SearchScreenViewIntent.OnCategoryClick -> {
                    updateSelectedCategory(intent.category)
                    searchImages()
                }

                is SearchScreenViewIntent.OnChangeSortOrder -> {
                    updateSortOrder(intent.sortOrder)
                    searchImages()
                }

                is SearchScreenViewIntent.OnImageClick -> {
                    _singleEvent.emit(SearchScreenSingleEvent.NavigateToCompressor(intent.imageUrl))
                }

                is SearchScreenViewIntent.OnScroll -> {
                    updateScrollPosition(intent.newFirstVisibleItem)
                }

            }
        }
    }

    private fun getCategories() {
        launch(
            coroutineContext = Dispatchers.Default
        ) {
            var categorySelected = false

            _state.update { state ->
                state.copy(
                    isLoading = false,
                    categories = getCategoriesUseCase().map { categoryDomainModel ->
                        categoryDomainModel.let {
                            CategoryDomainUiMapper.mapTo(
                                model = it,
                                isSelected = !categorySelected
                            )
                        }.also {
                            categorySelected = true
                        }
                    }
                )
            }
        }
    }

    private fun searchImages() {
        launch {
            _state.update { state ->
                state.copy(
                    searchPagingItems = searchImagesByQueryUseCase(
                        query = state.categories.find {
                            it.isSelected
                        }?.categoryName?.name?.lowercase().orEmpty(),
                        sortOrder = state.selectedSortOrder.name.lowercase(),
                    )
                        .distinctUntilChanged()
                        .map { pagingData ->
                            pagingData.map { item ->
                                item.let(SearchDomainUiMapper::mapTo)
                            }
                        }
                        .cachedIn(viewModelScope)
                )
            }
        }
    }

    private fun updateSelectedCategory(
        selectedCategoryName: CategoryNameUi
    ) {
        _state.update { state ->
            state.copy(
                categories = state.categories.map { category ->
                    category.copy(
                        isSelected = category.categoryName == selectedCategoryName
                    )
                }
            )
        }
    }

    private fun updateSortOrder(
        sortOrder: SortOrder
    ) {
        _state.update { state ->
            state.copy(
                selectedSortOrder = sortOrder
            )
        }
    }

    private fun updateScrollPosition(newScrollIndex: Int) {
        if (newScrollIndex == lastScrollIndex) return

        _state.update { state ->
            state.copy(
                categoriesIsHidden = newScrollIndex > lastScrollIndex
            )
        }

        lastScrollIndex = newScrollIndex
    }
}