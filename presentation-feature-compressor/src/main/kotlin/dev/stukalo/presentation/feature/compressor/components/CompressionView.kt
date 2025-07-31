package dev.stukalo.presentation.feature.compressor.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.stukalo.presentation.core.ui.components.PESpacer
import dev.stukalo.presentation.core.ui.components.Text
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.ui.theme.LocalPETypography
import dev.stukalo.presentation.core.localization.R
import dev.stukalo.presentation.feature.compressor.CompressorScreenViewIntent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CompressionView(
    modifier: Modifier = Modifier,
    isViewVisible: Boolean,
    isCompressionSliderVisible: Boolean,
    compressionLevel: Int,
    onEvent: (CompressorScreenViewIntent) -> Unit = {},
) {

    val sliderState = remember {
        SliderState(
            value = compressionLevel.toFloat(),
            steps = 99,
            valueRange = 0f..100f
        )
    }

    LaunchedEffect(sliderState.value) {
        onEvent(
            CompressorScreenViewIntent.OnChangeCompressionLevel(sliderState.value)
        )
    }

    AnimatedVisibility(
        visible = isViewVisible,
        enter = expandVertically(
            animationSpec = tween(durationMillis = 200),
            clip = false
        ) + fadeIn(),
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = 200),
            clip = false
        ) + fadeOut()
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    style = LocalPETypography.current.titleLarge,
                    text = stringResource(R.string.compressor_compression),
                    modifier = Modifier.clickable {
                        onEvent(CompressorScreenViewIntent.OnChangeCompressionVisibility)
                    }
                )

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = compressionLevel.toString(),
                    style = LocalPETypography.current.bodyLarge.copy(
                        color = LocalPEColors.current.textTitle
                    )
                )
            }

            AnimatedVisibility(
                visible = isCompressionSliderVisible,
                enter = expandVertically(
                    animationSpec = tween(durationMillis = 200),
                    clip = false
                ) + fadeIn(),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = 200),
                    clip = false
                ) + fadeOut()
            ) {
                Slider(
                    state = sliderState,
                    thumb = {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(LocalPEColors.current.primary)
                        )
                    },
                    track = { sliderState ->
                        val fraction by remember {
                            derivedStateOf {
                                (sliderState.value - sliderState.valueRange.start) /
                                        (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(fraction)
                                    .height(4.dp)
                                    .clip(CircleShape)
                                    .background(LocalPEColors.current.primary)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .clip(CircleShape)
                                    .background(LocalPEColors.current.icon)
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .height(20.dp)
                        .fillMaxWidth()
                )
            }
            PESpacer(36.dp)
        }
    }
}