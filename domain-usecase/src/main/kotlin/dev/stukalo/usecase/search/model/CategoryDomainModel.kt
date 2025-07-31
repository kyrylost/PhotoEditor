package dev.stukalo.usecase.search.model

import dev.stukalo.usecase.core.model.base.BaseDomainModel

data class CategoryDomainModel(
    val categoryName: CategoryNameDomain,
    val imageUrl: String,
) : BaseDomainModel
