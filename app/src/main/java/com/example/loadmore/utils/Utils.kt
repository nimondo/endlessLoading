package com.example.loadmore.utils

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.loadmore.R
import com.example.loadmore.apiKey
import java.io.File
import java.util.*

class Utils constructor(private val context: Context) {
    private var toast: Toast? = null


    fun errorToast(msg: String) {
        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        val view = toast?.view
        view?.setBackgroundResource(R.color.colorAccent)
        val text = view?.findViewById<View>(android.R.id.message) as TextView
        text.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        text.setPadding(15, 5, 15, 5)
        toast?.show()
    }

    fun successToast(msg: String) {
        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        val view = toast?.view
        view?.setBackgroundResource(R.color.colorPrimaryDark)
        val text = view?.findViewById(android.R.id.message) as TextView
        text.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        text.setPadding(15, 5, 15, 5)
        toast?.show()
    }

    fun successToastWithBackgroundRed(msg: String) {
        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        val view = toast?.view
        view?.setBackgroundResource(R.color.colorPrimary)
        val text = view?.findViewById(android.R.id.message) as TextView
        text.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        text.setPadding(15, 5, 15, 5)
        toast?.show()
    }
    fun isConnectedToInternet(): Boolean {
        val cm =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val ni = cm.activeNetwork
            val capabilities = cm.getNetworkCapabilities(ni)
            capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            ) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } else {
            val activeNetwork = cm.activeNetworkInfo
            activeNetwork != null && activeNetwork.isConnected
        }
    }
    fun showLoadingDialog(): MaterialDialog {
        return MaterialDialog(context).customView(R.layout.loading_view).cancelOnTouchOutside(false)
            .cornerRadius(16f)
    }
}