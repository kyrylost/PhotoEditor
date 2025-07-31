package dev.stukalo.presentation.feature.search.core.model

import androidx.compose.runtime.Immutable
import dev.stukalo.presentation.core.model.base.BaseUiModel

@Immutable
internal data class CategoryUiModel(
    val categoryName: CategoryNameUi,
    val imageUrl: String,
    val isSelected: Boolean = false
) : BaseUiModel
