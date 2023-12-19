package com.example.pmdm_p5_bookshelf

import android.app.Application
import com.example.pmdm_p5_bookshelf.data.AppContainer
import com.example.pmdm_p5_bookshelf.data.DefaultAppContainer

/**
 * Clase de la aplicación que se inicializa al iniciar la aplicación.
 */
class BookshelfApplication : Application() {
    // Contenedor para la gestión de dependencias
    lateinit var container: AppContainer

    // Inicializa un contenedor de implementación predeterminada
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}