package dev.stukalo.data.network.unsplash.model


import dev.stukalo.data.network.core.model.base.BaseDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDataModel(
    @SerialName("id")
    val id: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("urls")
    val urls: UrlsDataModel,
): BaseDataModel