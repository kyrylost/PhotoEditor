package dev.stukalo.presentation.feature.search.core.model

import dev.stukalo.presentation.core.model.base.BaseUiModel

internal enum class CategoryNameUi(val displayName: String) : BaseUiModel {
    NATURE("Nature"),
    ANIMALS("Animals"),
    TRAVEL("Travel"),
    PEOPLE("People"),
    ARCHITECTURE("Architecture"),
}
