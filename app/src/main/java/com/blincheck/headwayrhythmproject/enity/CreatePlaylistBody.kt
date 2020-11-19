package com.blincheck.headwayrhythmproject.enity

import com.google.gson.annotations.SerializedName

data class CreatePlaylistBody(@SerializedName("playlistName") val playlistName: String)