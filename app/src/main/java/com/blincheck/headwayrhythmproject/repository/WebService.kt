package com.blincheck.headwayrhythmproject.repository

import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.enity.User
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WebService {

    @GET("Tracks")
    fun getAllTracks(): Single<List<Track>>

    @GET("User")
    fun getUser(): Single<List<User>>

    @Multipart
    @POST("User/update")
    fun updateUser(
        @Part("userId") userId: Int,
        @Part("Username") userName: RequestBody,
        @Part("Description") description: RequestBody,
        @Part filePart: MultipartBody.Part? = null
    ): Single<User>

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