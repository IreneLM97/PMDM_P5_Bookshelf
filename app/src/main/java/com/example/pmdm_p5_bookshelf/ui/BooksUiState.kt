package com.example.pmdm_p5_bookshelf.ui

import com.example.pmdm_p5_bookshelf.model.Book

sealed interface BooksUiState {
    data object Loading : BooksUiState
    data object Error : BooksUiState
    data class Success(val books: List<Book>) : BooksUiState
}