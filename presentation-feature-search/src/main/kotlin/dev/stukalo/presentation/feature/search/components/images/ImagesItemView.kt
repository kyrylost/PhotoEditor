package dev.stukalo.presentation.feature.search.components.images

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import dev.stukalo.presentation.core.ui.components.forwardingPainter
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.ui.theme.LocalPEShape
import dev.stukalo.presentation.feature.search.R
import dev.stukalo.presentation.feature.search.SearchScreenViewIntent

@Composable
internal fun ImagesItemView(
    modifier: Modifier = Modifier,
    height: Int = 0,
    width: Int = 0,
    imageUrl: String,
    onImageClick: (SearchScreenViewIntent) -> Unit = {},
) {

    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    val imageHeight = with(LocalDensity.current) {
        (height * (screenWidth / width)).toDp()
    }

    AsyncImage(
        model = imageUrl.toUri(),
        placeholder = forwardingPainter(
            painter = painterResource(R.drawable.placeholder),
            colorFilter = ColorFilter.tint(color = LocalPEColors.current.icon)
        ),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier
            .heightIn(max = 400.dp)
            .height(imageHeight)
            .fillMaxWidth()
            .clip(
                shape = LocalPEShape.current.largeRoundedCornerShape
            )
            .clickable {
                onImageClick(SearchScreenViewIntent.OnImageClick(imageUrl))
            }
    )
}

@ThemedPreview
@Composable
private fun PreviewSearchItemsList() {
    Preview {
        ImagesItemView(
            imageUrl = ""
        )
    }
}