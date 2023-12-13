package com.example.pmdm_p5_bookshelf.data

import com.example.pmdm_p5_bookshelf.model.Book
import com.example.pmdm_p5_bookshelf.network.BookshelfApiService

class DefaultBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfRepository {
    override suspend fun getBooks(): List<Book> = bookshelfApiService.getBooks()
}