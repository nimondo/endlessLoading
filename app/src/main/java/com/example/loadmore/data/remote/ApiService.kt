package com.example.loadmore.data.remote

import com.example.loadmore.data.model.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    fun getPosts():  Observable<MutableList<Post>>
}