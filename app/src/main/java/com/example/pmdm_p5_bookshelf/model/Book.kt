package com.example.pmdm_p5_bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val title: String,
    @SerialName("img_src") val imgSrc: String
)