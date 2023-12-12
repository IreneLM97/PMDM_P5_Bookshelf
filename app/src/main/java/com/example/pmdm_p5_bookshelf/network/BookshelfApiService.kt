package com.example.pmdm_p5_bookshelf.network

import com.example.pmdm_p5_bookshelf.model.Book
import retrofit2.http.GET

interface BookshelfApiService {
    @GET("books")
    suspend fun getBooks(): List<Book>
}