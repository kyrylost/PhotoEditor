package dev.stukalo.data.network.impl.di

import dev.stukalo.data.network.impl.core.client.base.client
import dev.stukalo.data.network.impl.source.unsplash.UnsplashNetSourceImpl
import dev.stukalo.data.network.unsplash.source.UnsplashNetSource
import dev.stukalo.data.network.impl.core.client.unsplash.unsplashClient
import org.koin.dsl.module

val dataNetworkModule = module {
    single { client() }
    single<UnsplashNetSource> { UnsplashNetSourceImpl( unsplashClient(get() )) }
}