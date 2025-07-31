package dev.stukalo.data.repository.search.model

import dev.stukalo.data.repository.core.model.base.BaseRepositoryModel

data class SearchResultRepositoryModel(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String,
) : BaseRepositoryModel
