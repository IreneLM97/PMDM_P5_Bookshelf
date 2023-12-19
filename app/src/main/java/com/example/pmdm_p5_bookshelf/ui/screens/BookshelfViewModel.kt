package com.example.pmdm_p5_bookshelf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pmdm_p5_bookshelf.BookshelfApplication
import com.example.pmdm_p5_bookshelf.data.BookshelfRepository
import com.example.pmdm_p5_bookshelf.model.Book
import kotlinx.coroutines.launch
import retrofit2.http.Query

sealed interface BookshelfUiState {
    data class Success(val books: List<Book>) : BookshelfUiState
    data object Error : BookshelfUiState
    data object Loading : BookshelfUiState

}

class BookshelfViewModel(
    private val bookshelfRepository: BookshelfRepository
) : ViewModel() {

    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    init {
        getBooks()
    }

    fun getBooks(@Query("q") query: String = "maths") {
        if(query.isEmpty()) return
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Loading
            val result = bookshelfRepository.getBooks(query)
            bookshelfUiState = result?.let {
                BookshelfUiState.Success(it)
            } ?: BookshelfUiState.Error
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}
