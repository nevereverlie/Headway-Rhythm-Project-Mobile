package com.blincheck.headwayrhythmproject.enity

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("userId") val userId: Int,
    @SerializedName("token") val token: String
)