package dev.stukalo.usecase.impl.search.mapper

import dev.stukalo.data.repository.search.model.SearchResultRepositoryModel
import dev.stukalo.usecase.core.mapper.base.BaseRepositoryDomainMapper
import dev.stukalo.usecase.search.model.SearchResultDomainModel

object SearchRepositoryDomainMapper :
    BaseRepositoryDomainMapper<SearchResultRepositoryModel, SearchResultDomainModel> {
    override fun mapTo(model: SearchResultRepositoryModel): SearchResultDomainModel =
        with(model) {
            SearchResultDomainModel(
                id = id,
                width = width,
                height = height,
                url = url,
            )
        }

    override fun mapFrom(model: SearchResultDomainModel): SearchResultRepositoryModel =
        with(model) {
            SearchResultRepositoryModel(
                id = id,
                width = width,
                height = height,
                url = url,
            )
        }
}
