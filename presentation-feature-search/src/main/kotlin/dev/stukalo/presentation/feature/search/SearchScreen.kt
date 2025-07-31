package dev.stukalo.presentation.feature.search

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.stukalo.presentation.core.ui.platform.OnActionHandler
import dev.stukalo.presentation.feature.search.navigation.SearchScreenAction
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    onAction: OnActionHandler = {},
) {

    val viewModel: SearchViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalActivity.current as ComponentActivity

    LaunchedEffect(Unit) {
        viewModel.singleEvent.collectLatest { singleEvent ->
            when(singleEvent) {
                is SearchScreenSingleEvent.NavigateToCompressor -> {
                    onAction(SearchScreenAction.GoToProcessing(singleEvent.imageUrl))
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        context.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )
    }

    with(state) {
        SearchView(
            modifier = modifier
                .statusBarsPadding()
                .navigationBarsPadding(),
            isLoading = isLoading,
            isError = isError,
            searchPagingItems = searchPagingItems,
            categories = categories,
            categoriesIsHidden = categoriesIsHidden,
            selectedSortOrder = selectedSortOrder,
            gridType = gridType,
            onIntent = viewModel::consumeIntent,
        )
    }
}
