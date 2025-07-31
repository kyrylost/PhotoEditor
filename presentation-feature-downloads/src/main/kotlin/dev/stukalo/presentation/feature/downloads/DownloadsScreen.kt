package dev.stukalo.presentation.feature.downloads

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.stukalo.presentation.core.ui.components.ErrorWithRetry
import dev.stukalo.presentation.core.ui.components.Loader
import org.koin.androidx.compose.koinViewModel

@Composable
fun DownloadsScreen(
    modifier: Modifier = Modifier
) {

    val viewModel: DownloadsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.consumeIntent(
            DownloadsScreenViewIntent.OnLoadDownloads
        )
    }

    Box(modifier = modifier
        .fillMaxSize()
        .navigationBarsPadding()
        .statusBarsPadding()
    ) {
        with(state) {
            if(isLoading) {
                Loader(modifier = Modifier.fillMaxSize())
            } else if(isError) {
                ErrorWithRetry(modifier = Modifier.fillMaxSize()) {
                    viewModel.consumeIntent(
                        DownloadsScreenViewIntent.OnLoadDownloads
                    )
                }
            } else {
                DownloadsView(
                    downloads = downloads
                )
            }
        }
    }
}
