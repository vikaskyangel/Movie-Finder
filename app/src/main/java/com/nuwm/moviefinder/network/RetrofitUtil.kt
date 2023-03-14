package com.nuwm.moviefinder.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitUtil {
    companion object {
        var URL = "https://api.themoviedb.org/"
        val moviesApi: MovieApi
            get() {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
                val gson = GsonBuilder().create()
                val retrofit: Retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(URL)
                    .client(client)
                    .build()
                return retrofit.create(MovieApi::class.java)
            }
    }
}