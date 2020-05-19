package com.example.loadmore.utils

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.example.loadmore.utils.listener.HttpErrorListener
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Scheduler
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*

interface IRxSchedulers {
    fun main(): Scheduler
    fun io(): Scheduler
}

object Common {
    internal fun Activity.showSnackbarLong(text: CharSequence) {
        Snackbar.make(
            findViewById(R.id.content),
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }

    internal fun Activity.showSnackbarLong(@StringRes text: Int) {
        Snackbar.make(
            findViewById(R.id.content),
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }

    internal fun Activity.showSnackbarInternetProblem(@StringRes text: Int, context: Context) {
        val snackbar = Snackbar.make(
            findViewById(R.id.content),
            text,
            Snackbar.LENGTH_LONG
        )
        val v = snackbar.view
        val color = v.resources.getColor(com.example.loadmore.R.color.colorPrimary)
        v.background.setColorFilter(color,
            PorterDuff.Mode.SRC_ATOP
        )
        snackbar.setAction("Settings") {
            context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
        }.setActionTextColor(
            ContextCompat.getColor(
                context,
                com.example.loadmore.R.color.colorWhite
            )
        )
            .show()
    }

    fun getErrorMessageByLanguage(responseBody: ResponseBody, lang: String): String {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            if (lang == "en") jsonObject.getJSONObject("message").get("en").toString()
            else jsonObject.getJSONObject("message").get("fr").toString()
        } catch (e: Exception) {
            e.message!!
        }
    }
    fun getErrorMessage(responseBody: ResponseBody): String {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            jsonObject.getString("message")
        } catch (e: Exception) {
            e.message!!
        }
    }
    fun getErrorFromObservable(e: Throwable, httpErrorListener: HttpErrorListener) {
        when (e) {
            is HttpException -> {
                val responseBody = e.response()!!.errorBody()
                if (e.response()!!.code() == 460 || e.response()!!.code() == 461) {
                    if (Locale.getDefault().language == "en") {
                        httpErrorListener.errorMessage(
                            getErrorMessageByLanguage(responseBody!!, "en")
                        )
                    } else httpErrorListener.errorMessage(
                        getErrorMessageByLanguage(responseBody!!, "fr")
                    )
                } else
                    httpErrorListener.errorMessage(getErrorMessage(responseBody!!))
            }
            is SocketTimeoutException -> {
                httpErrorListener.timeOutError()
            }
            is IOException -> {
                httpErrorListener.networkFailure()
            }
            else -> {
                httpErrorListener.errorMessage(e.message!!)
            }
        }
    }

}