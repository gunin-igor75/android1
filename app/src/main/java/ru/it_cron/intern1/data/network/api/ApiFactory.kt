package ru.it_cron.intern1.data.network.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://services.it-cron.ru/api/"
    private const val HEADER_LANGUAGE = "Accept-Language"
    private const val HEADER_LANGUAGE_VALUE = "ru"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(setHeader())
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

    private fun loginInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun setHeader(): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request()
                .newBuilder()
                .addHeader(HEADER_LANGUAGE, HEADER_LANGUAGE_VALUE)
                .build()
            return@Interceptor chain.proceed(newBuilder)
        }
    }
}