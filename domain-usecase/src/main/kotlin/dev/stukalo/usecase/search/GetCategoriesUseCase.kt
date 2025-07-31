package dev.stukalo.usecase.search

import dev.stukalo.usecase.search.model.CategoryDomainModel

interface GetCategoriesUseCase {
    suspend operator fun invoke(): List<CategoryDomainModel>
}