package com.blincheck.headwayrhythmproject.enity

import com.google.gson.annotations.SerializedName

data class Track (
    @SerializedName("trackId") var trackId: Int,
    @SerializedName("name") var name: String,
    @SerializedName("artist") var artist: String,
    @SerializedName("album") var album: String,
    @SerializedName("duration") var duration: Int,
    @SerializedName("genres") var genres: String
)