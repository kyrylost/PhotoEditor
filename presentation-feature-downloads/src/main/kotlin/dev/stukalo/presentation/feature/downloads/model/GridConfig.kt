package dev.stukalo.presentation.feature.downloads.model

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier

data class GridConfig(
    val columns: Int,
    val verticalArrangement: Arrangement.Vertical,
    val horizontalArrangement: Arrangement.Horizontal,
    val contentPadding: PaddingValues,
    val imageModifier: Modifier
)