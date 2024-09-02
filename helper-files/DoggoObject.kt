package com.droidcon.doggodelight.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DoggoObject(
    val name: String,
    @SerialName("bred_for") val bredFor: String? = null,
    @SerialName("breed_group") val breedGroup: String? = null,
    @SerialName("country_code") val countryCode: String? = null,
    val description: String? = null,
    val height: Height,
    val history: String? = null,
    val id: Int,
    val image: Image,
    @SerialName("life_span") val lifeSpan: String,
    val origin: String? = null,
    @SerialName("reference_image_id") val referenceImageId: String,
    val temperament: String? = "",
    val weight: Weight
)

@Serializable
data class Weight(
    val imperial: String,
    val metric: String
)

@Serializable
data class Height(
    val imperial: String,
    val metric: String
)


@Serializable
data class Image(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)