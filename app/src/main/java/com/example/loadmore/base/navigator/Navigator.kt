package com.example.loadmore.base.navigator

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

abstract class Navigator {
    abstract fun getActivity(): Activity
    fun startActivityWithData(
        activityClass: Class<out AppCompatActivity>,
        extraValue: Map<String, Parcelable>
    ) {
        val activity = getActivity()
        val intent = Intent(activity, activityClass)
        if (extraValue.isNotEmpty()) {
            for (i in extraValue) {
                intent.putExtra(i.key, i.value as Parcelable)
            }
        }
        activity.startActivity(intent)
        activity.finish()
    }
    fun startActivityWithObject(
        activityClass: Class<out AppCompatActivity>,
        extraValue: Map<String, Any>
    ) {
        val gson: Gson = Gson()
        val activity = getActivity()
        val intent = Intent(activity, activityClass)
        if (extraValue.isNotEmpty()) {
            for (i in extraValue) {
                intent.putExtra(i.key, gson.toJson(i.value))
            }
        }
        activity.startActivity(intent)
        activity.finish()
    }
    fun startActivity(activityClass: Class<out AppCompatActivity>) {
        val activity = getActivity()
        val intent = Intent(activity, activityClass)
        activity.startActivity(intent)
    }
}