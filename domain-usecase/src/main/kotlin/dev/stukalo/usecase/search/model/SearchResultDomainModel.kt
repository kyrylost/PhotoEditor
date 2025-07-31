package dev.stukalo.usecase.search.model

import dev.stukalo.usecase.core.model.base.BaseDomainModel

data class SearchResultDomainModel(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String,
) : BaseDomainModel
