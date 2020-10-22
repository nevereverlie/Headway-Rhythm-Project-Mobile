package com.blincheck.headwayrhythmproject.repository

import android.net.Uri
import android.util.Log
import com.blincheck.headwayrhythmproject.enity.User
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UserRepository {

    private val webService = WebService.getWebService()

    fun getUser(): Single<List<User>> = webService.getUser()

    fun updateUser(user: User, photoUri: Uri?): Single<User> {
        val file = if (photoUri == null) null else File(photoUri.path!!)
        val filePart = if (file == null) null
        else MultipartBody.Part.createFormData(
            "file",
            file.name,
            RequestBody.create("image/*".toMediaTypeOrNull(), file)
        )

        val userName = RequestBody.create("text/plain".toMediaTypeOrNull(), user.userName!!)
        val description = RequestBody.create("text/plain".toMediaTypeOrNull(), user.description!!)

        return webService.updateUser(
            user.userId,
            userName,
            description,
            filePart
        )
    }
}