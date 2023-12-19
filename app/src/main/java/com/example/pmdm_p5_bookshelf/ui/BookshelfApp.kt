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

/**
 * Función que representa la estructura principal de la aplicación.
 *
 * @param viewModel ViewModel que gestiona el estado de la aplicación.
 * @param navController Controlador de navegación para gestionar la navegación entre pantallas.
 */
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
        // Pantalla que muestra el filtro de búsqueda y la lista de libros (pantalla principal)
        composable(route = BookshelfAppScreen.SearchScreen.name) {
            SearchScreen(
                viewModel = viewModel,
                onBookClick = {
                    viewModel.updateCurrentBook(it)  // Actualizamos libro seleccionado
                    navController.navigate(BookshelfAppScreen.DetailScreen.name)  // Navegamos a la pantalla de detalle
                }
            )
        }

        // Pantalla que muestra el detalle de un libro específico
        composable(route = BookshelfAppScreen.DetailScreen.name) {
            val context = LocalContext.current
            DetailScreen(
                viewModel = viewModel,
                onBackPressed = {
                    navController.popBackStack()  // Retrocedemos en la pila de navegación
                },
                onSendButtonClicked = { summary: String ->
                    sharePlace(context, summary = summary)  // Compartimos la información
                }
            )
        }
    }
}

/**
 * Función que permite compartir la información de un libro a otra aplicación.
 *
 * @param context Contexto de la aplicación.
 * @param summary Resumen del libro que se quiere compartir.
 */
private fun sharePlace(
    context: Context,
    summary: String
) {
    // Crear un Intent de acción SEND para compartir
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"  // Contenido de texto plano
        putExtra(Intent.EXTRA_TEXT, summary)  // Agregamos resumen
    }

    // Iniciar una actividad para elegir la aplicación de destino a la que se quiere compartir
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.send_book)
        )
    )
}