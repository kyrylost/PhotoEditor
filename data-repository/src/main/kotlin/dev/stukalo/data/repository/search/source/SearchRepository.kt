package dev.stukalo.data.repository.search.source

import androidx.paging.PagingData
import dev.stukalo.data.repository.search.model.SearchResultRepositoryModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun search(
        query: String,
        sortOrder: String,
    ): Flow<PagingData<SearchResultRepositoryModel>>
}
