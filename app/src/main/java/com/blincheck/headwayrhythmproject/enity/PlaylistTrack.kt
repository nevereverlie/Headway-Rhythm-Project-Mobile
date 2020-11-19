package com.blincheck.headwayrhythmproject.enity

import com.google.gson.annotations.SerializedName

data class PlaylistTrack(
    @SerializedName("trackId") val trackId: Int,
    @SerializedName("track") val track: Track,
    @SerializedName("playlistId") val playlistId: Int
)