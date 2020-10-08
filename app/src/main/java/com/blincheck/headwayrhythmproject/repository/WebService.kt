package com.blincheck.headwayrhythmproject.repository

import com.blincheck.headwayrhythmproject.enity.Track
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WebService {

    @GET("/Tracks")
    fun getAllTracks(): Single<List<Track>>

    companion object {

        private const val BASE_URL = "https://localhost:5001/api/"

        fun getWebService(): WebService {
            val httpClient = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build()
                .create(WebService::class.java)
        }
    }
}