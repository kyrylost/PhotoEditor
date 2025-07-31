package dev.stukalo.data.repository.impl.di

import dev.stukalo.data.repository.impl.search.source.SearchRepositoryImpl
import dev.stukalo.data.repository.search.source.SearchRepository
import org.koin.dsl.module

val dataRepositorySearchModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get()) }
}