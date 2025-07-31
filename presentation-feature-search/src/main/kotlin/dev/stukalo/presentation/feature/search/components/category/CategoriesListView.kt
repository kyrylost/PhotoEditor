package dev.stukalo.presentation.feature.search.components.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.util.ext.shadowWithOffset
import dev.stukalo.presentation.feature.search.SearchScreenViewIntent
import dev.stukalo.presentation.feature.search.core.model.CategoryNameUi
import dev.stukalo.presentation.feature.search.core.model.CategoryUiModel

@Composable
internal fun CategoriesListView(
    modifier: Modifier = Modifier,
    categories: List<CategoryUiModel>,
    onCategoryClick: (SearchScreenViewIntent) -> Unit = {},
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(24.dp)
    ) {
        items(
            items = categories,
            key = { it.categoryName.ordinal }
        ) { category ->
            CategoriesItemView(
                modifier = Modifier.shadowWithOffset(
                    blurRadius = if (!category.isSelected) 16.dp else 6.dp,
                    clip = 24.dp,
                    offsetX = 8.dp,
                    offsetY = 8.dp
                ),
                category = category,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@ThemedPreview
@Composable
private fun PreviewCategoriesListView() {
    Preview(showBackground = true) {
        CategoriesListView(
            categories = listOf(
                CategoryUiModel(
                    categoryName = CategoryNameUi.NATURE,
                    imageUrl = ""
                ),
                CategoryUiModel(
                    categoryName = CategoryNameUi.ARCHITECTURE,
                    imageUrl = ""
                ),
                CategoryUiModel(
                    categoryName = CategoryNameUi.PEOPLE,
                    imageUrl = ""
                ),
            ),
        )
    }
}
