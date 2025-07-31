package dev.stukalo.usecase.impl.search.di

import dev.stukalo.usecase.search.GetCategoriesUseCase
import dev.stukalo.usecase.search.SearchImagesByQueryUseCase
import dev.stukalo.usecase.impl.search.GetCategoriesUseCaseImpl
import dev.stukalo.usecase.impl.search.SearchImagesByQueryUseCaseImpl
import org.koin.dsl.module

val domainUseCaseModule = module {
    single<SearchImagesByQueryUseCase> { SearchImagesByQueryUseCaseImpl( get() ) }
    single<GetCategoriesUseCase> { GetCategoriesUseCaseImpl() }
}