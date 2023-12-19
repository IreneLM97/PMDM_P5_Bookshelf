package com.example.pmdm_p5_bookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.pmdm_p5_bookshelf.ui.BookshelfApp
import com.example.pmdm_p5_bookshelf.ui.theme.PMDM_P5_BookshelfTheme

/**
 * Actividad principal que define la estructura inicial de la aplicación.
 */
class MainActivity : ComponentActivity() {
    /**
     * Método que se ejecuta al crear la actividad.
     *
     * @param savedInstanceState Objeto Bundle que contiene el estado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PMDM_P5_BookshelfTheme {
                // Contenedor de la aplicación
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Iniciamos la aplicación de Bookshelf
                    BookshelfApp()
                }
            }
        }
    }
}