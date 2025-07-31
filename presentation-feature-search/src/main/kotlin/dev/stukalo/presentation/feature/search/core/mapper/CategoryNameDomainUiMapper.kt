package dev.stukalo.presentation.feature.search.core.mapper

import dev.stukalo.presentation.core.mapper.base.BaseUiDomainMapper
import dev.stukalo.presentation.feature.search.core.model.CategoryNameUi
import dev.stukalo.usecase.search.model.CategoryNameDomain

internal object CategoryNameDomainUiMapper : BaseUiDomainMapper<CategoryNameDomain, CategoryNameUi> {
    override fun mapTo(model: CategoryNameDomain): CategoryNameUi = CategoryNameUi.valueOf(model.name)
}
