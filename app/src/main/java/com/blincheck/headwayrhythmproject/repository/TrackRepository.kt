package com.blincheck.headwayrhythmproject.repository

import com.blincheck.headwayrhythmproject.enity.Track
import io.reactivex.Single

class TrackRepository {

    private val webService = WebService.getWebService()

    fun getAllTracks() = webService.getAllTracks()
}