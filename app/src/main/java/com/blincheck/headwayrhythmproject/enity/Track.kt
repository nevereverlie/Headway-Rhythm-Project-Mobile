package com.blincheck.headwayrhythmproject.enity

import com.google.gson.annotations.SerializedName

data class Track (
    @SerializedName("trackId") var trackId: Int,
    @SerializedName("trackName") var trackName: String?,
    @SerializedName("performerName") var performerName: String?,
    @SerializedName("url") var url: String,
    @SerializedName("publicId") var publicId: String,
    @SerializedName("trackYear") var dateAdded: Int,
    @SerializedName("genres") var genres: String?
)