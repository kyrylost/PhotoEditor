package dev.stukalo.presentation.feature.search.core.model

import dev.stukalo.presentation.core.model.base.BaseUiModel

data class SearchResultUiModel(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String,
) : BaseUiModel