package dev.stukalo.photoeditor

import android.app.Application
import dev.stukalo.presentation.service.image.downloader.di.serviceImageDownloaderModule
import dev.stukalo.data.network.impl.di.dataNetworkModule
import dev.stukalo.data.repository.impl.di.dataRepositorySearchModule
import dev.stukalo.domain.usecase.android.impl.core.di.domainUseCaseAndroidJavaModule
import dev.stukalo.domain.usecase.android.impl.core.di.domainUseCaseAndroidModule
import dev.stukalo.presentation.feature.compare.di.featureCompareModule
import dev.stukalo.presentation.feature.compressor.di.featureCompressorModule
import dev.stukalo.presentation.feature.downloads.di.featureDownloadsModule
import dev.stukalo.presentation.feature.logs.di.featureLogsModule
import dev.stukalo.presentation.feature.search.di.featureSearchModule
import dev.stukalo.presentation.navigation.processing.flow.di.processingFlowModule
import dev.stukalo.usecase.impl.search.di.domainUseCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PEApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PEApplication)
            modules(
                listOf(
                    // core
                    serviceImageDownloaderModule,

                    //data
                    dataNetworkModule,
                    dataRepositorySearchModule,

                    //domain
                    domainUseCaseModule,
                    domainUseCaseAndroidModule,
                    domainUseCaseAndroidJavaModule,

                    //feature
                    featureSearchModule,
                    featureDownloadsModule,

                    processingFlowModule,
                    featureCompressorModule,
                    featureCompareModule,
                    featureLogsModule,
                )
            )
        }
    }
}