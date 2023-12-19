package com.example.pmdm_p5_bookshelf.data

import com.example.pmdm_p5_bookshelf.network.BookshelfApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Interfaz que define un contenedor para las dependencias de la aplicación.
 */
interface AppContainer {
    val bookshelfRepository: BookshelfRepository
}

/**
 * Implementación predeterminada de AppContainer.
 * Esta clase inicializa y proporciona las dependencias de la aplicación (servicio Retrofit y repositorio de la biblioteca).
 */
class DefaultAppContainer : AppContainer {
    // URL base para la API de Google Books
    private val baseUrl = "https://www.googleapis.com/books/v1/"

    // Configuración de JSON para la serialización y deserialización de datos
    private val json = Json { ignoreUnknownKeys = true }

    // Configuración y creación de la instancia de Retrofit para la comunicación con la API
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    // Creación del servicio Retrofit a partir de la interfaz BookshelfApiService
    private val retrofitService: BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }

    // Creación y provisión del repositorio de la biblioteca
    override val bookshelfRepository: BookshelfRepository by lazy {
        DefaultBookshelfRepository(retrofitService)
    }
}