package com.example.loadmore.ui.main

import android.content.Context
import com.example.loadmore.base.BaseViewModel
import com.example.loadmore.base.navigator.Navigator
import com.example.loadmore.utils.IRxSchedulers
import com.example.loadmore.utils.listener.HttpErrorListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class MainViewModel (
    val context: Context
    ) : BaseViewModel(),
    KodeinAware {
        override val kodein by closestKodein(context)
        override val kodeinContext = kcontext(context)
        private val schedulers: IRxSchedulers by instance()
        private val navigator: Navigator by instance()
        lateinit var httpErrorListener: HttpErrorListener
        val ctxt : Context = context
}