package com.blincheck.headwayrhythmproject.repository

import com.blincheck.headwayrhythmproject.enity.Track
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WebService {

    @GET("Tracks")
    fun getAllTracks(): Single<List<Track>>

    companion object {

        private const val BASE_URL = "https://hrp-api.herokuapp.com/api/"

        fun getWebService(): WebService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(WebService::class.java)
        }
    }
}