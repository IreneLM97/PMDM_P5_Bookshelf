package com.example.pmdm_p5_bookshelf.network

import com.example.pmdm_p5_bookshelf.model.Book
import retrofit2.http.GET

interface BookshelfApiService {
    @GET("books")  // TODO HACER FILTRO PARA LA BÚSQUEDA DE LIBROS
    suspend fun getBooks(): List<Book>
}