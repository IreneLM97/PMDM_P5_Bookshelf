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
    @SerialName("authors") val authors: List<String>? = null,
    @SerialName("publishedDate") val publishedDate: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("imageLinks") val imageLinks: Thumbnails?
) {
    val bookAuthors: String
        get() = authors?.joinToString(", ") ?: "No authors"

    val bookPublished: String
        get() = publishedDate ?: "No date"

    val bookDescription: String
        get() = description ?: "No description"
}

@Serializable
data class Thumbnails(
    @SerialName("thumbnail") val thumbnail: String
) {
    val httpsThumbnail: String
        get() = thumbnail.replace("http", "https")
}