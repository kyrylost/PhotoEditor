package dev.stukalo.presentation.feature.search.components.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.stukalo.presentation.core.ui.components.PESpacer
import dev.stukalo.presentation.core.ui.components.Text
import dev.stukalo.presentation.core.ui.preview.Preview
import dev.stukalo.presentation.core.ui.preview.ThemedPreview
import dev.stukalo.presentation.core.ui.theme.LocalPETypography
import dev.stukalo.presentation.core.localization.R
import dev.stukalo.presentation.feature.search.SearchScreenViewIntent
import dev.stukalo.presentation.feature.search.core.model.CategoryNameUi
import dev.stukalo.presentation.feature.search.core.model.CategoryUiModel

@Composable
internal fun CategoriesView(
    categoriesIsHidden: Boolean,
    categories: List<CategoryUiModel>,
    onCategoryClick: (SearchScreenViewIntent) -> Unit = {},
) {
    AnimatedVisibility(
        visible = !categoriesIsHidden,
        enter = expandVertically(
            animationSpec = tween(durationMillis = 200),
            clip = false
        ) + fadeIn(),
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = 200),
            clip = false
        ) + fadeOut()
    ) {
        Column {
            Row(
                modifier = Modifier.padding(24.dp),
            ) {
                Text(
                    style = LocalPETypography.current.titleNormal,
                    text = stringResource(R.string.search_category),
                )

                PESpacer(1f)

                Text(
                    style = LocalPETypography.current.titleNormal.copy(color = dev.stukalo.presentation.core.ui.theme.LocalPEColors.current.primary),
                    text = stringResource(R.string.search_see_more),
                )
            }

            CategoriesListView(
                categories = categories,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@ThemedPreview
@Composable
private fun PreviewCategoriesView() {
    Preview(showBackground = true) {
        CategoriesView(
            categoriesIsHidden = false,
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
