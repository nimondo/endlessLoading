package com.example.loadmore.ui.post

import android.content.Context
import com.example.loadmore.utils.Common
import com.example.loadmore.base.BaseViewModel
import com.example.loadmore.data.repositories.PostRepository
import com.example.loadmore.utils.IRxSchedulers
import com.example.loadmore.utils.listener.HttpErrorListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class PostViewModel(
    private val postRepository: PostRepository,
    val context: Context
) : BaseViewModel(),
    KodeinAware {
    override val kodein by closestKodein(context)
    override val kodeinContext = kcontext(context)
    private val schedulers: IRxSchedulers by instance()
    lateinit var httpErrorListener: HttpErrorListener
    lateinit var postListener: PostListener
    val postListAdapter: PostListAdapter = PostListAdapter(this)
    fun getAllArticles(isConnect:Boolean) {
        httpErrorListener.loadingProgressbar()
        addDisposable(
            postRepository.getArticles(isConnect)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.main())
                .subscribe(
                    { result ->
                        httpErrorListener.hideLoading()
                        postListAdapter.updatePostList(result)
                        if (result.isEmpty()) {
                            postListener.emptyData()
                        } else {
                            postListener.hideEmptyData()
                                postListAdapter.updatePostList(result.sortedWith(compareByDescending({ it.id })))
                        }
                    },
                    { e ->
                        Common.getErrorFromObservable(e, httpErrorListener)
                    }
                )
        )
    }
}