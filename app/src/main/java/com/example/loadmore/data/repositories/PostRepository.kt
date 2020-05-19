package com.example.loadmore.data.repositories

import android.util.Log
import com.example.loadmore.data.local.dao.PostDao
import com.example.loadmore.data.model.Post
import com.example.loadmore.data.remote.ApiService
import com.example.loadmore.utils.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class PostRepository(
    private val apiInterfaceService: ApiService,
    private val postDao: PostDao,
    val utils: Utils
) {
    fun getArticles(connect:Boolean): Observable<MutableList<Post>> {
        return Observable.fromCallable { postDao.all }
            .concatMap { dbPostList ->
                if (dbPostList.isNotEmpty()){
                    dbPostList.sortBy { it.id }
                    val from = dbPostList[dbPostList.size-1].id
                    Log.i("Articles", connect.toString())
                    if(connect){
                        apiInterfaceService.getPosts().concatMap { apiPostList ->
                            postDao.insertAll(*apiPostList.toTypedArray())
                            val posts: List<Post> = dbPostList + apiPostList
                            Log.i("ArticlesPost1", posts.toString())
                            posts.sortedWith(compareByDescending({ it.id }))
                            Log.i("ArticlesPost2", posts.toString())
                            Observable.just(posts.toMutableList())
                        }

                    }else{
                        Observable.just(dbPostList)
                    }
                }
                else
                    apiInterfaceService.getPosts().concatMap { apiPostList ->
                        postDao.insertAll(*apiPostList.toTypedArray())
                        Observable.just(apiPostList)
                    }
            }
    }
}