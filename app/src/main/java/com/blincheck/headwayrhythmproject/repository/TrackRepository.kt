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
                    trackName = "temp_name_1",
                    performerName = "temp_p_name_1",
                    url = "https://res.cloudinary.com/headway-rhythm-project/video/upload/v1602164546/t0lyjdncyuahppcolpa3.mp3",
                    publicId = "t0lyjdncyuahppcolpa3",
                    dateAdded = "0001-01-01T00:00:00",
                    genres = null
                ),
                Track(
                    trackId = 2,
                    trackName = "temp_name_2",
                    performerName = "temp_p_name_2",
                    url = "https://res.cloudinary.com/headway-rhythm-project/video/upload/v1602164552/wpodzw36tu90lkl6mhz2.mp3",
                    publicId = "wpodzw36tu90lkl6mhz2",
                    dateAdded = "0001-01-01T00:00:00",
                    genres = null
                ),
                Track(
                    trackId = 3,
                    trackName = "temp_name_3",
                    performerName = "temp_p_name_3",
                    url = "https://res.cloudinary.com/headway-rhythm-project/video/upload/v1602164555/kyhqstokwhbeantxrkkf.mp3",
                    publicId = "kyhqstokwhbeantxrkkf",
                    dateAdded = "0001-01-01T00:00:00",
                    genres = null
                ),
                Track(
                    trackId = 4,
                    trackName = "temp_name_4",
                    performerName = "temp_p_name_4",
                    url = "https://res.cloudinary.com/headway-rhythm-project/video/upload/v1602175165/tkoqqqk3bbz97johiivg.mp3",
                    publicId = "tkoqqqk3bbz97johiivg",
                    dateAdded = "0001-01-01T00:00:00",
                    genres = null
                ),
                Track(
                    trackId = 5,
                    trackName = "temp_name_5",
                    performerName = "temp_p_name_5",
                    url = "https://res.cloudinary.com/headway-rhythm-project/video/upload/v1602175190/ycmmk9wdocdkl5seufsb.mp3",
                    publicId = "ycmmk9wdocdkl5seufsb",
                    dateAdded = "0001-01-01T00:00:00",
                    genres = null
                ),
                Track(
                    trackId = 6,
                    trackName = null,
                    performerName = null,
                    url = "https://res.cloudinary.com/headway-rhythm-project/video/upload/v1602175608/jyouerzpaoldmfo6wzzy.mp3",
                    publicId = "jyouerzpaoldmfo6wzzy",
                    dateAdded = "0001-01-01T00:00:00",
                    genres = null
                ),
                Track(
                    trackId = 7,
                    trackName = "sssssssss",
                    performerName = "ssssss",
                    url = "https://res.cloudinary.com/headway-rhythm-project/video/upload/v1602175776/sgipntp2tek2qa5gaguc.mp3",
                    publicId = "sgipntp2tek2qa5gaguc",
                    dateAdded = "0001-01-01T00:00:00",
                    genres = null
                )
            )
        }
    }
}