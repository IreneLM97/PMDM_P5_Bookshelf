package com.example.pmdm_p5_bookshelf

import android.app.Application
import com.example.pmdm_p5_bookshelf.data.AppContainer
import com.example.pmdm_p5_bookshelf.data.DefaultAppContainer

class BookshelfApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}