package dev.stukalo.usecase.impl.search

import dev.stukalo.usecase.search.GetCategoriesUseCase
import dev.stukalo.usecase.search.model.CategoryDomainModel
import dev.stukalo.usecase.search.model.CategoryNameDomain

internal class GetCategoriesUseCaseImpl: GetCategoriesUseCase {
    override suspend fun invoke(): List<CategoryDomainModel> = listOf(
        CategoryDomainModel(
            categoryName = CategoryNameDomain.NATURE,
            imageUrl = NATURE_IMG_URL
        ),
        CategoryDomainModel(
            categoryName = CategoryNameDomain.ANIMALS,
            imageUrl = ANIMALS_IMG_URL
        ),
        CategoryDomainModel(
            categoryName = CategoryNameDomain.PEOPLE,
            imageUrl = PEOPLE_IMG_URL
        ),
        CategoryDomainModel(
            categoryName = CategoryNameDomain.TRAVEL,
            imageUrl = TRAVEL_IMG_URL
        ),
        CategoryDomainModel(
            categoryName = CategoryNameDomain.ARCHITECTURE,
            imageUrl = ARCHITECTURE_IMG_URL
        ),
    )
}

const val NATURE_IMG_URL = "https://images.unsplash.com/photo-1514917860136-ee8b88e8c9c9?q=80&w=400&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
const val ANIMALS_IMG_URL = "https://images.unsplash.com/photo-1525241697266-55d2da7edbdd?q=80&w=400&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
const val PEOPLE_IMG_URL = "https://images.unsplash.com/photo-1668363393525-c0c07fe33586?q=80&w=400&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
const val TRAVEL_IMG_URL = "https://images.unsplash.com/photo-1543337542-9c915b01789e?q=80&w=400&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
const val ARCHITECTURE_IMG_URL = "https://images.unsplash.com/photo-1733418750591-6b47d7614c7b?q=80&w=400&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"