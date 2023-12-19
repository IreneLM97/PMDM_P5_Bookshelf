package com.example.pmdm_p5_bookshelf.ui.screens.detail

import com.example.pmdm_p5_bookshelf.model.Book

data class DetailUiState(
    val currentBook: Book?
)

val initialState = DetailUiState(
    currentBook = null
)