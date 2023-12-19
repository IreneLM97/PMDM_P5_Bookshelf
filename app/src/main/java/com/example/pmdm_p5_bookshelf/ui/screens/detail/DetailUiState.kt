package com.example.pmdm_p5_bookshelf.ui.screens.detail

import com.example.pmdm_p5_bookshelf.model.Book

/**
 * Modelo que representa el estado de la interfaz de usuario para los detalles de un libro.
 *
 */
data class DetailUiState(
    val currentBook: Book?
)

/**
 * Estado inicial por defecto para la pantalla de detalles.
 */
val initialState = DetailUiState(
    currentBook = null
)