package com.example.pmdm_p5_bookshelf.ui.screens.search

import com.example.pmdm_p5_bookshelf.model.Book

sealed interface SearchUiState {
    data object Loading : SearchUiState
    data object Error : SearchUiState
    data class Success(val books: List<Book>) : SearchUiState
}