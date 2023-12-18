package com.example.pmdm_p5_bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksList(
    @SerialName("items") val books: List<Book>?
)