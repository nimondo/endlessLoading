package com.example.loadmore

import android.app.Application
import android.content.Context
import com.example.loadmore.kdi.appModule
import com.example.loadmore.kdi.networkModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class LoadMoreApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        bind<Context>("ApplicationContext") with singleton { this@LoadMoreApplication.applicationContext }
        bind<LoadMoreApplication>() with singleton { this@LoadMoreApplication }
        import(appModule)
        import(networkModule)
    }

    override fun onCreate() {
        super.onCreate()
    }
}