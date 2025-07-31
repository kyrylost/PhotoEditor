package dev.stukalo.data.repository.impl.search.mapper

import dev.stukalo.data.network.unsplash.model.SearchResultDataModel
import dev.stukalo.data.network.unsplash.model.UrlsDataModel
import dev.stukalo.data.repository.core.mapper.base.BaseDataRepositoryMapper
import dev.stukalo.data.repository.search.model.SearchResultRepositoryModel

object SearchDataRepositoryMapper :
    BaseDataRepositoryMapper<SearchResultDataModel, SearchResultRepositoryModel> {
    override fun mapTo(model: SearchResultDataModel): SearchResultRepositoryModel =
        with(model) {
            SearchResultRepositoryModel(
                id = id,
                width = width,
                height = height,
                url = urls.regular,
            )
        }

    override fun mapFrom(model: SearchResultRepositoryModel): SearchResultDataModel =
        with(model) {
            SearchResultDataModel(
                id = id,
                width = width,
                height = height,
                urls = UrlsDataModel(
                    regular = url,
                    full = "",
                    raw = "",
                    small = "",
                    smallS3 = "",
                    thumb = "",
                ),
            )
        }
}
