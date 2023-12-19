package com.example.pmdm_p5_bookshelf.ui

import android.net.wifi.hotspot2.pps.HomeSp
import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pmdm_p5_bookshelf.R
import com.example.pmdm_p5_bookshelf.model.Book
import com.example.pmdm_p5_bookshelf.ui.screens.detail.DetailScreen
import com.example.pmdm_p5_bookshelf.ui.screens.search.SearchScreen

/**
 * Enumeración que define los posibles estados de pantalla de la aplicación.
 */
enum class BookshelfAppScreen {
    SearchScreen,
    DetailScreen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(
    viewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    // Configuración del sistema de navegación
    NavHost(
        navController = navController,
        startDestination = BookshelfAppScreen.SearchScreen.name,
        modifier = Modifier.fillMaxSize()
    ) {
        // Estructura de la pantalla que muestra la lista de categorías (pantalla principal)
        composable(route = BookshelfAppScreen.SearchScreen.name) {
            SearchScreen(
                viewModel = viewModel,
                onBookClick = {
                    viewModel.updateCurrentBook(it)
                    navController.navigate(BookshelfAppScreen.DetailScreen.name)
                }
            )
        }

        // Estructura de pantalla que muestra el detalle del libro
        composable(route = BookshelfAppScreen.DetailScreen.name) {
            DetailScreen(
                viewModel = viewModel,
                onBackPressed = {
                    navController.popBackStack()  // retroceder en la pila de navegación
                }
            )
        }
    }
}