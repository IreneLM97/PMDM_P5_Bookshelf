package com.example.pmdm_p5_bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelo de datos serializable que representa una lista de libros.
 *
 * @property books Lista de libros o nulo si no hay libros disponibles.
 */
@Serializable
data class BooksList(
    @SerialName("items") val books: List<Book>?
)