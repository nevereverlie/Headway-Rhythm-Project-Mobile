package com.blincheck.headwayrhythmproject.enity

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId") val userId: Int,
    @SerializedName("username") val userName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("photoUrl") val photoUrl: String?,
    @SerializedName("publicId") val publicId: String?
)