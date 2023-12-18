package com.example.pmdm_p5_bookshelf.data

import com.example.pmdm_p5_bookshelf.model.Book

interface BookshelfRepository {
    suspend fun getBooks(query: String): List<Book>?
}