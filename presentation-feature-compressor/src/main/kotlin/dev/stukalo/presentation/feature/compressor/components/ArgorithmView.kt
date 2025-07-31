package dev.stukalo.presentation.feature.compressor.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dev.stukalo.presentation.core.ui.components.Text
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.ui.theme.LocalPETypography
import dev.stukalo.presentation.core.ui.util.threePagesPerViewport
import dev.stukalo.presentation.feature.compressor.CompressorScreenViewIntent
import dev.stukalo.presentation.core.ui.model.Algorithm
import dev.stukalo.presentation.core.localization.R
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.absoluteValue

@Composable
internal fun AlgorithmView(
    modifier: Modifier = Modifier,
    isAlgorithmPager: Boolean,
    algorithms: List<Algorithm>,
    onEvent: (CompressorScreenViewIntent) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        pageCount = {
            algorithms.size
        },
        initialPage = 2
    )

    val pagerOffsetFractionChanged by remember {
        derivedStateOf {
            pagerState.currentPageOffsetFraction == 0f
        }
    }

    val pagerUserScrollEnabled = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(pagerOffsetFractionChanged) {
        if (pagerOffsetFractionChanged) {
            snapshotFlow { pagerState.currentPage }.collectLatest { page ->
                pagerUserScrollEnabled.value = false
                if (page == 1) {
                    pagerState.requestScrollToPage(5)
                } else if(page == 6) {
                    pagerState.requestScrollToPage(2)
                }
                onEvent(CompressorScreenViewIntent.OnAlgorithmPagerScroll(page))
                pagerUserScrollEnabled.value = true
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .clickable {
                    onEvent(CompressorScreenViewIntent.OnChangeAlgorithmVisibility)
                },
            style = LocalPETypography.current.titleLarge,
            text = stringResource(R.string.compressor_algorithm),
        )

        AnimatedVisibility(
            visible = isAlgorithmPager,
            enter = expandVertically(
                animationSpec = tween(durationMillis = 200),
                clip = false
            ) + fadeIn(),
            exit = shrinkVertically(
                animationSpec = tween(durationMillis = 200),
                clip = false
            ) + fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = pagerUserScrollEnabled.value,
                    snapPosition = SnapPosition.Center,
                    pageSize = threePagesPerViewport
                ) { page ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                val pageOffset = (
                                        (pagerState.currentPage - page) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue

                                scaleX = lerp(
                                    start = 0.8f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                                scaleY = lerp(
                                    start = 0.8f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            },

                        style = LocalPETypography.current.bodyNormal.copy(
                            textAlign = TextAlign.Center,

                            fontWeight = if (pagerState.currentPage == page)
                                FontWeight.Medium
                            else
                                FontWeight.Normal,

                            color = if (pagerState.currentPage == page)
                                LocalPEColors.current.primary
                            else
                                LocalPEColors.current.textBody
                        ),

                        text = algorithms[page].title,
                    )
                }
            }
        }
    }
}