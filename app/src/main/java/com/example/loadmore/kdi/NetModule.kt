package com.example.loadmore.kdi

import android.content.Context
import com.example.loadmore.BuildConfig
import com.example.loadmore.KEY_APK
import com.example.loadmore.data.remote.ApiService
import com.example.loadmore.rEQUESTTIMEOUT
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

private const val MODULE_NAME = "Network Module"

val networkModule = Kodein.Module(MODULE_NAME, false) {
    bind<OkHttpClient>() with singleton { getMockOkHttpClient(instance()) }
    bind<Retrofit>() with singleton { getMockRetrofit(instance(), instance()) }
    bind<ApiService>() with singleton { getMockApiService(instance()) }
    bind<Moshi>() with singleton { providesMoshi() }
}

private fun getMockOkHttpClient(context: Context): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val builder: OkHttpClient.Builder = OkHttpClient.Builder()
    builder.interceptors().add(httpLoggingInterceptor)
    builder.readTimeout(rEQUESTTIMEOUT, TimeUnit.SECONDS)
    builder.connectTimeout(rEQUESTTIMEOUT, TimeUnit.SECONDS)

    builder.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
        requestBuilder.addHeader("Accept-App", KEY_APK)
//        if (!TextUtils.isEmpty(Utils(context).getStoredApiKey())) {
//            requestBuilder.addHeader(
//                "Authoriz-API-X",
//                "Bearer " + Objects.requireNonNull<String>(Utils(context).getStoredApiKey())
//            )
//        }
        val request = requestBuilder.build()
        it.proceed(request)
    }
    return builder.build()
}

private fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private fun getMockRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder().client(okHttpClient).baseUrl(BuildConfig.URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

private fun getMockApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)
