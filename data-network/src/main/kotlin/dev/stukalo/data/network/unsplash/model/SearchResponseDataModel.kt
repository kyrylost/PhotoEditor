package dev.stukalo.data.network.unsplash.model


import dev.stukalo.data.network.core.model.base.BaseDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDataModel(
    @SerialName("results")
    val results: List<SearchResultDataModel>,
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int
): BaseDataModel