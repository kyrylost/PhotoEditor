package dev.stukalo.data.repository.impl.search.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.stukalo.data.network.unsplash.source.UnsplashNetSource
import dev.stukalo.data.repository.search.model.SearchResultRepositoryModel
import dev.stukalo.data.repository.search.source.SearchRepository
import dev.stukalo.data.repository.impl.search.source.paging.SearchPagingSource
import kotlinx.coroutines.flow.Flow

const val RESULTS_PER_PAGE = 14

internal class SearchRepositoryImpl(
    private val searchNetSource: UnsplashNetSource,
): SearchRepository {

    override suspend fun search(
        query: String,
        sortOrder: String,
    ): Flow<PagingData<SearchResultRepositoryModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = RESULTS_PER_PAGE,
                prefetchDistance = 1,
                initialLoadSize = RESULTS_PER_PAGE
            ),
            pagingSourceFactory = {
                SearchPagingSource(
                    unsplashNetSource = searchNetSource,
                    query = query,
                    sortOrder = sortOrder,
                )
            }
        ).flow
    }
}