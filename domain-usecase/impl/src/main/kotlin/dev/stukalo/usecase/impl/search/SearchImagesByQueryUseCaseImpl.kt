package dev.stukalo.usecase.impl.search

import androidx.paging.PagingData
import androidx.paging.map
import dev.stukalo.data.repository.search.source.SearchRepository
import dev.stukalo.usecase.impl.search.mapper.SearchRepositoryDomainMapper
import dev.stukalo.usecase.search.SearchImagesByQueryUseCase
import dev.stukalo.usecase.search.model.SearchResultDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SearchImagesByQueryUseCaseImpl(
    private val searchRepository: SearchRepository,
): SearchImagesByQueryUseCase {
    override suspend operator fun invoke(
        query: String,
        sortOrder: String,
    ): Flow<PagingData<SearchResultDomainModel>> {
        return searchRepository.search(query, sortOrder).map { pagingData ->
            pagingData.map { item ->
                item.let(SearchRepositoryDomainMapper::mapTo)
            }
        }
    }
}