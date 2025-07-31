package dev.stukalo.presentation.feature.search.core.mapper

import dev.stukalo.presentation.core.mapper.base.BaseUiDomainMapper
import dev.stukalo.presentation.feature.search.core.model.SearchResultUiModel
import dev.stukalo.usecase.search.model.SearchResultDomainModel

object SearchDomainUiMapper :
    BaseUiDomainMapper<SearchResultDomainModel, SearchResultUiModel> {
    override fun mapTo(model: SearchResultDomainModel): SearchResultUiModel =
        with(model) {
            SearchResultUiModel(
                id = id,
                width = width,
                height = height,
                url = url,
            )
        }

    override fun mapFrom(model: SearchResultUiModel): SearchResultDomainModel =
        with(model) {
            SearchResultDomainModel(
                id = id,
                width = width,
                height = height,
                url = url,
            )
        }
}
