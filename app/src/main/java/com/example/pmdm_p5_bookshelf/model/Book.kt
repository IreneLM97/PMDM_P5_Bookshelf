package com.example.pmdm_p5_bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Clase de datos serializable que representa un libro.
 *
 * @property volumeInfo Información detallada del libro.
 */
@Serializable
data class Book(
    @SerialName("volumeInfo") val volumeInfo: VolumeInfo
)

/**
 * Datos serializables que representan la información detallada de un libro.
 *
 * @property title Título del libro.
 * @property authors Lista de autores del libro o nulo si no hay autores.
 * @property publishedDate Fecha de publicación del libro o nulo si no hay fecha.
 * @property description Descripción del libro o nulo si no hay descripción.
 * @property imageLinks Enlaces de imágenes del libro.
 */
@Serializable
data class VolumeInfo(
    @SerialName("title") val title: String,
    @SerialName("authors") val authors: List<String>? = null,
    @SerialName("publishedDate") val publishedDate: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("imageLinks") val imageLinks: Thumbnails?
) {
    // Devuelve cadena con los autores del libro o No authors
    val bookAuthors: String
        get() = authors?.joinToString(", ") ?: "No authors"

    // Devuelve la fecha de publicación o No date
    val bookPublished: String
        get() = publishedDate ?: "No date"

    // Devuelve la descripción del libro o No description
    val bookDescription: String
        get() = description ?: "No description"
}

/**
 * Datos serializables que representan las miniaturas de imágenes de un libro.
 *
 * @property thumbnail URL de la miniatura de la imagen.
 */
@Serializable
data class Thumbnails(
    @SerialName("thumbnail") val thumbnail: String
) {
    // Devuelve la URL de la miniatura del libro con HTTPS en lugar de HTTP.
    val httpsThumbnail: String
        get() = thumbnail.replace("http", "https")
}