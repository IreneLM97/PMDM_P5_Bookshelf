package com.example.pmdm_p5_bookshelf.network

import com.example.pmdm_p5_bookshelf.model.BooksList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz que define los métodos para realizar llamadas a la API de la biblioteca de libros.
 */
interface BookshelfApiService {
    /**
     * Realiza una solicitud GET para obtener una lista de libros según la consulta proporcionada.
     *
     * @param query Consulta para buscar libros (por defecto: maths).
     * @return [Response] que contiene una lista de libros ([BooksList]) obtenida de la API.
     */
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String = "maths"
    ): Response<BooksList>
}