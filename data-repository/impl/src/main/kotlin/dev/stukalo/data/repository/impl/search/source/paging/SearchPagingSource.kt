package dev.stukalo.data.repository.impl.search.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.stukalo.data.network.unsplash.source.UnsplashNetSource
import dev.stukalo.data.repository.impl.search.mapper.SearchDataRepositoryMapper
import dev.stukalo.data.repository.search.model.SearchResultRepositoryModel


internal class SearchPagingSource(
    private val unsplashNetSource: UnsplashNetSource,
    private val query: String,
    private val sortOrder: String,
): PagingSource<Int, SearchResultRepositoryModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultRepositoryModel> {
        return try {
            val currentPage = params.key ?: 1
            println("currentPage${currentPage}")
            println("loadSize${params.loadSize}")
            val searchResponse = unsplashNetSource.get(
                query = query,
                sortOrder = sortOrder,
                page = currentPage,
                perPage = params.loadSize,
            )
            LoadResult.Page(
                data = searchResponse.results.map { it.let(SearchDataRepositoryMapper::mapTo) },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (searchResponse.results.isEmpty()) null else currentPage + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchResultRepositoryModel>): Int? {
        return state.anchorPosition
    }

}
