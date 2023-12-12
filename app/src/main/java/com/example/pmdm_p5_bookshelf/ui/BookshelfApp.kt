package com.example.pmdm_p5_bookshelf.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.pmdm_p5_bookshelf.R
import com.example.pmdm_p5_bookshelf.ui.screens.BookshelfViewModel
import com.example.pmdm_p5_bookshelf.ui.screens.HomeScreen
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp() {
    Scaffold(
        topBar = {
            // Barra superior personalizada
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.bookshelf))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(R.color.my_dark_blue)
                )
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val bookshelfViewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)
            HomeScreen(
                bookshelfUiState = bookshelfViewModel.bookshelfUiState,
                retryAction = bookshelfViewModel::getBooks,
                modifier = Modifier.fillMaxSize(),
                contentPadding = it
            )
        }
    }
}