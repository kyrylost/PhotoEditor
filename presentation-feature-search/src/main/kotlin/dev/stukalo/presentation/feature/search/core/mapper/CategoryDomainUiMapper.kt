package dev.stukalo.presentation.feature.search.core.mapper

import dev.stukalo.presentation.core.mapper.base.BaseUiDomainMapper
import dev.stukalo.presentation.feature.search.core.model.CategoryUiModel
import dev.stukalo.usecase.search.model.CategoryDomainModel

internal object CategoryDomainUiMapper : BaseUiDomainMapper<CategoryDomainModel, CategoryUiModel> {
    fun mapTo(model: CategoryDomainModel, isSelected: Boolean): CategoryUiModel =
        with(model) {
            CategoryUiModel(
                categoryName = categoryName.let(CategoryNameDomainUiMapper::mapTo),
                imageUrl = imageUrl,
                isSelected = isSelected
            )
        }
}
