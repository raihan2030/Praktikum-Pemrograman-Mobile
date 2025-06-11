package com.pemrogramanmobile.apicompose.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "91a08b3c95e46f3b968e7dfdfc6c81b0"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            val requestBuilder = originalRequest.newBuilder().url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    val instance: TmdbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(TmdbApiService::class.java)
    }
}