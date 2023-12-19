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
import com.example.pmdm_p5_bookshelf.ui.screens.detail.DetailUiState
import com.example.pmdm_p5_bookshelf.ui.screens.detail.initialState
import com.example.pmdm_p5_bookshelf.ui.screens.search.SearchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookshelfViewModel(
    private val bookshelfRepository: BookshelfRepository
) : ViewModel() {

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val searchUiState = _searchUiState.asStateFlow()

    private val _detailUiState = MutableStateFlow<DetailUiState>(initialState)
    val detailUiState = _detailUiState.asStateFlow()

    init {
        getBooks()
    }

    fun getBooks(query: String = "maths") {
        if (query.isEmpty()) return
        viewModelScope.launch {
            _searchUiState.value = SearchUiState.Loading
            val result = bookshelfRepository.getBooks(query)
            _searchUiState.value = result?.let {
                SearchUiState.Success(it)
            } ?: SearchUiState.Error
        }
    }

    fun updateCurrentBook(
        selectedBook: Book
    ) {
        _detailUiState.update {
            it.copy(
                currentBook = selectedBook
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}