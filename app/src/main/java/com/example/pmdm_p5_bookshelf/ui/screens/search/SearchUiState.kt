package com.example.pmdm_p5_bookshelf.ui.screens.search

import com.example.pmdm_p5_bookshelf.model.Book

/**
 * Define un sello de estado sellado para la pantalla de búsqueda.
 * Tres estados posibles: Loading (carga), Error (error) y Success (éxito)
 */
sealed interface SearchUiState {
    data object Loading : SearchUiState // Carga
    data object Error : SearchUiState // Error
    data class Success(val books: List<Book>) : SearchUiState // Éxito (con una lista de libros)
}