package com.example.pmdm_p5_bookshelf.network

import com.example.pmdm_p5_bookshelf.model.BooksList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String = "maths"
    ): Response<BooksList>
}