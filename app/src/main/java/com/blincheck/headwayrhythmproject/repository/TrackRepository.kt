package com.blincheck.headwayrhythmproject.repository

import com.blincheck.headwayrhythmproject.enity.Track
import io.reactivex.Single

class TrackRepository {

    private val webService = WebService.getWebService()

    fun getAllTracks(): Single<List<Track>> {
        //return webService.getAllTracks()
        return Single.fromCallable {
            listOf(
                Track(
                    trackId = 1,
                    trackName = "gachi1 - 1 - 01",
                    performerName = "gachi1",
                    url = "https://res.cloudinary.com/dvfiem4yb/video/upload/v1602267976/gold_jfohn0.mp3",
                    publicId = "t0lyjdncyuahppcolpa3",
                    dateAdded = "2001",
                    genres = ""
                ),
                Track(
                    trackId = 2,
                    trackName = "gaachi2 - 1 - 11",
                    performerName = "gachi1",
                    url = "https://res.cloudinary.com/dvfiem4yb/video/upload/v1602267934/%D0%A4%D1%80%D0%B5%D0%BD%D0%B4%D0%B7%D0%BE%D0%BD%D0%B0_-_BOY%D1%87%D0%B8%D0%BA_rework_vlmrnz.mp3",
                    publicId = "wpodzw36tu90lkl6mhz2",
                    dateAdded = "2011",
                    genres = ""
                ),
                Track(
                    trackId = 3,
                    trackName = "gachi3 - 2 - 21",
                    performerName = "gachi2",
                    url = "https://res.cloudinary.com/dvfiem4yb/video/upload/v1602267934/%D0%A0%D1%83%D1%81%D1%81%D0%BA%D0%B8%D0%B5_MC_Z_-_%D0%A7%D0%B8%D1%81%D1%82%D0%BE%D0%B5_%D0%BD%D0%B5%D0%B1%D0%BE_Right_version_Gachi_Remix_GachiBass_hegcv8.mp3",
                    publicId = "kyhqstokwhbeantxrkkf",
                    dateAdded = "2021",
                    genres = ""
                )
            )
        }
    }
}