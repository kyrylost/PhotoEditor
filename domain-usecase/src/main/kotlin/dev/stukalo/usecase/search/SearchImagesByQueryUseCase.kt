package dev.stukalo.usecase.search

import androidx.paging.PagingData
import dev.stukalo.usecase.search.model.SearchResultDomainModel
import kotlinx.coroutines.flow.Flow

interface SearchImagesByQueryUseCase {
    suspend operator fun invoke(
        query: String,
        sortOrder: String,
//        itemShouldBeVisible: Int,
    ): Flow<PagingData<SearchResultDomainModel>>
}