package com.blincheck.headwayrhythmproject.repository

import com.blincheck.headwayrhythmproject.enity.*
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface WebService {

    @GET("Tracks")
    fun getAllTracks(@Header("authorization") token: String): Single<List<Track>>

    @Headers("Content-Type: application/json")
    @POST("Playlists/add-track-to-playlist/{userId}")
    fun addTrackToPlaylist(
        @Path("userId") userId: Int,
        @Header("authorization") token: String,
        @Body addToPlayListRequestBody: AddToPlayListRequestBody
    ): Completable

    @GET("User")
    fun getUser(@Header("authorization") token: String): Single<List<User>>

    @GET("Playlists/get-playlists-of-user/{userId}")
    fun getUserPlaylists(
        @Header("authorization") token: String,
        @Path("userId") userId: Int
    ): Single<List<Playlist>>

    @POST("Playlists/add-playlist-for-user/{userId}")
    fun createPlaylist(
        @Header("authorization") token: String,
        @Path("userId") userId: Int,
        @Body createPlaylistBody: CreatePlaylistBody
    ): Completable

    @Multipart
    @PUT("User/update")
    fun updateUser(
        @Header("authorization") token: String,
        @Part("userId") userId: Int,
        @Part("Username") userName: RequestBody,
        @Part("Description") description: RequestBody,
        @Part filePart: MultipartBody.Part? = null
    ): Single<User>

    @Headers("Content-Type: application/json")
    @POST("Auth/login")
    fun loginUser(@Body requestBody: LoginRequest): Single<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("Auth/register")
    fun registerUser(@Body requestBody: LoginRequest): Completable

    companion object {

        private const val BASE_URL = "https://hrp-api.herokuapp.com/api/"

        var token: String = ""
        set(value) { field = "Bearer " + value }

        fun getWebService(): WebService {
            val clientBuilder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.apply {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
            clientBuilder.addInterceptor(loggingInterceptor)

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(WebService::class.java)
        }
    }
}