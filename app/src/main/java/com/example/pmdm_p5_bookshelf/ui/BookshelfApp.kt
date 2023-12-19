package com.example.pmdm_p5_bookshelf.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pmdm_p5_bookshelf.R
import com.example.pmdm_p5_bookshelf.ui.screens.detail.DetailScreen
import com.example.pmdm_p5_bookshelf.ui.screens.search.SearchScreen

/**
 * Enumeración que define los posibles estados de pantalla de la aplicación.
 */
enum class BookshelfAppScreen {
    SearchScreen,
    DetailScreen
}

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
            val context = LocalContext.current
            DetailScreen(
                viewModel = viewModel,
                onBackPressed = {
                    navController.popBackStack()  // retroceder en la pila de navegación
                },
                onSendButtonClicked = { summary: String ->
                    sharePlace(context, summary = summary)  // compartimos la información
                }
            )
        }
    }
}

/**
 * Función que permite compartir la información de un lugar a otra aplicación.
 *
 * @param context contexto de la aplicación
 * @param summary resumen del lugar que se quiere compartir
 */
private fun sharePlace(
    context: Context,
    summary: String
) {
    // Crear un Intent de acción SEND para compartir
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"  // contenido de texto plano
        putExtra(Intent.EXTRA_TEXT, summary)  // agregamos resumen
    }

    // Iniciar una actividad para elegir la aplicación de destino a la que se quiere compartir
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.send_book)
        )
    )
}