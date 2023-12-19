package com.example.pmdm_p5_bookshelf.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pmdm_p5_bookshelf.BookshelfApplication
import com.example.pmdm_p5_bookshelf.data.BookshelfRepository
import com.example.pmdm_p5_bookshelf.model.Book
import com.example.pmdm_p5_bookshelf.ui.screens.detail.initialState
import com.example.pmdm_p5_bookshelf.ui.screens.search.SearchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel que gestiona el estado de la interfaz de usuario.
 *
 * @param bookshelfRepository Repositorio para obtener datos de libros.
 */
class BookshelfViewModel(
    private val bookshelfRepository: BookshelfRepository
) : ViewModel() {
    // Estado de la pantalla de búsqueda
    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val searchUiState = _searchUiState.asStateFlow()

    // Estado de la pantalla de detalles
    private val _detailUiState = MutableStateFlow(initialState)
    val detailUiState = _detailUiState.asStateFlow()

    // Inicialización que se llama cuando se crea una instancia de ViewModel
    init {
        getBooks()
    }

    /**
     * Método para obtener libros según una consulta (por defecto: maths)
     *
     * @param query Consulta para buscar libros.
     */
    fun getBooks(query: String = "maths") {
        if (query.isEmpty()) return
        viewModelScope.launch {
            // Indica que está cargando y obtiene resultados
            _searchUiState.value = SearchUiState.Loading
            val result = bookshelfRepository.getBooks(query)

            // Actualiza el estado de la pantalla de búsqueda con los resultados obtenidos
            _searchUiState.value = result?.let {
                SearchUiState.Success(it)
            } ?: SearchUiState.Error
        }
    }

    /**
     * Método para actualizar el libro actual en la pantalla de detalles.
     *
     * @param selectedBook Libro seleccionado para mostrar detalles.
     */
    fun updateCurrentBook(
        selectedBook: Book
    ) {
        _detailUiState.update {
            it.copy(currentBook = selectedBook)
        }
    }

    companion object {
        // Factory para crear una instancia del ViewModel
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}