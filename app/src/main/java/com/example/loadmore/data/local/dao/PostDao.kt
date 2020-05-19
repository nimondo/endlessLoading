package com.example.loadmore.data.local.dao

import androidx.room.*
import com.example.loadmore.data.model.Post
import io.reactivex.Completable

@Dao
interface PostDao {
    @get:Query("SELECT * FROM post")
    val all: MutableList<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg post:Post)
    @Update
    fun update(post: Post): Completable


    @Delete
    fun delete(post: Post): Completable
}