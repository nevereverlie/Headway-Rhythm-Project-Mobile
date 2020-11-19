package com.blincheck.headwayrhythmproject.enity

import com.google.gson.annotations.SerializedName

data class AddToPlayListRequestBody(
    @SerializedName("trackId") val trackId: Int,
    @SerializedName("playlistId") val playlistId: Int
)