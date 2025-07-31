package dev.stukalo.presentation.feature.search.components.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import dev.stukalo.presentation.core.ui.components.PESpacer
import dev.stukalo.presentation.core.ui.components.Text
import dev.stukalo.presentation.core.ui.components.forwardingPainter
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.theme.LocalPEColors
import dev.stukalo.presentation.core.ui.theme.LocalPEShape
import dev.stukalo.presentation.core.ui.theme.LocalPETypography
import dev.stukalo.presentation.feature.search.R
import dev.stukalo.presentation.feature.search.SearchScreenViewIntent
import dev.stukalo.presentation.feature.search.core.model.CategoryNameUi
import dev.stukalo.presentation.feature.search.core.model.CategoryUiModel

@Composable
internal fun CategoriesItemView(
    modifier: Modifier = Modifier,
    category: CategoryUiModel,
    onCategoryClick: (SearchScreenViewIntent) -> Unit = {},
) {
    Column (
        modifier = modifier
            .clip(shape = LocalPEShape.current.largeRoundedCornerShape)
            .background(color = LocalPEColors.current.surface)
            .clickable {
                onCategoryClick(
                    SearchScreenViewIntent.OnCategoryClick(category.categoryName)
                )
            }
            .padding(8.dp),
    ) {
        AsyncImage(
            model = category.imageUrl.toUri(),
            placeholder = forwardingPainter(
                painter = painterResource(R.drawable.placeholder),
                colorFilter = ColorFilter.tint(color = LocalPEColors.current.icon)
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .width(120.dp)
                .height(136.dp)
                .clip(
                    shape = LocalPEShape.current.largeRoundedCornerShape
                )
        )

        PESpacer(8.dp)

        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
            style = LocalPETypography.current.titleSmall,
            text = category.categoryName.displayName
        )
    }
}

@ThemedPreview
@Composable
private fun PreviewCategoriesItemView() {
    Preview {
        CategoriesItemView(
            category = CategoryUiModel(
                categoryName = CategoryNameUi.NATURE,
                imageUrl = ""
            )
        )
    }
}