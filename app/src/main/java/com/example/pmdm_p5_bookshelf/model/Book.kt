package com.example.pmdm_p5_bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName("volumeInfo") val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    @SerialName("title") val title: String,
    @SerialName("imageLinks") val imageLinks: Thumbnails?
)

@Serializable
data class Thumbnails(
    @SerialName("thumbnail") val thumbnail: String
) {
    val httpsThumbnail: String
        get() = thumbnail.replace("http", "https")
}