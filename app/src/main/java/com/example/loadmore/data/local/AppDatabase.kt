package com.example.loadmore.data.local

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.loadmore.data.local.dao.PostDao
import com.example.loadmore.data.model.Post

@Database(
    entities = [Post::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}