package com.example.pmdm_p5_bookshelf.data

import com.example.pmdm_p5_bookshelf.network.BookshelfApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bookshelfRepository: BookshelfRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"  // TODO CAMBIAR DIRECCIÓN URL

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }

    override val bookshelfRepository: BookshelfRepository by lazy {
        DefaultBookshelfRepository(retrofitService)
    }
}