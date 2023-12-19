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

sealed interface HomeUiState {
    data class Success(val books: List<Book>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

class HomeViewModel(
    private val bookshelfRepository: BookshelfRepository
) : ViewModel() {

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getBooks()
    }

    fun getBooks(@Query("q") query: String = "maths") {
        if(query.isEmpty()) return
        viewModelScope.launch {
            homeUiState = HomeUiState.Loading
            val result = bookshelfRepository.getBooks(query)
            homeUiState = result?.let {
                HomeUiState.Success(it)
            } ?: HomeUiState.Error
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                HomeViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}
