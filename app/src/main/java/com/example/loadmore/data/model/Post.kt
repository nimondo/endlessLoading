package com.example.loadmore.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Post(
    @PrimaryKey
    var id: Long,
    var title :  String,
    var body : String
)