package com.example.pmdm_p5_bookshelf.data

import com.example.pmdm_p5_bookshelf.model.Book
import com.example.pmdm_p5_bookshelf.network.BookshelfApiService

class DefaultBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfRepository {
    override suspend fun getBooks(query: String): List<Book>? {
        return try {
            val response = bookshelfApiService.getBooks(query)
            if (response.isSuccessful) {
                val data = response.body()!!
                data.books ?: emptyList()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}