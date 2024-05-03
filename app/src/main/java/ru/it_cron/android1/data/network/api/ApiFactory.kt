package ru.it_cron.android1.data.network.api

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://services.it-cron.ru/api/"

    private val okHttpClient = getOrHttpBuilder()
        .addInterceptor(loginInterceptor())
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java) ?: throw IllegalStateException(
        "ApiService is null"
    )

    private fun getOrHttpBuilder(): OkHttpClient.Builder =
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            OkHttpClient.Builder()
        } else {
            UnsafeOkHttpClient.getUnsafeOkHttpClient()
        }

    private fun loginInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}