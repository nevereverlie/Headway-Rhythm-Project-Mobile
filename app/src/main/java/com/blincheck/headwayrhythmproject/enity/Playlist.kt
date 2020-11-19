package com.blincheck.headwayrhythmproject.enity

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("playlistId") val playlistId: Int,
    @SerializedName("playlistName") val playlistName: String,
    @SerializedName("playlistTracks") val playlistTracks: List<PlaylistTrack>?,
    @SerializedName("userId") val userId: Int
)