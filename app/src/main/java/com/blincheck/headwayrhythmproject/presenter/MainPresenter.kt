package com.blincheck.headwayrhythmproject.presenter

import android.util.Log
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.repository.TrackRepository
import com.blincheck.headwayrhythmproject.ui.MainActivity
import com.blincheck.headwayrhythmproject.util.PlayListManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(private var playListManager: PlayListManager) : BasePresenter<MainActivity>() {

    private val trackRepository = TrackRepository()

    private var searchString = ""
    private var filterMap : Map<String, String>? = null

    fun loadTracks() {
        trackRepository
            .getAllTracks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onTracksLoaded, ::onError)
    }

    private fun onTracksLoaded(tracks: List<Track>) {
        val filteredTracks: List<Track>
        if (!searchString.isEmpty())
            filteredTracks = filterTracks(tracks)
        else if (filterMap != null) {
            Log.d("OLEG", "Begin")
            filteredTracks = filterTracksPoBolshe(tracks)
            filterMap = null
        }
        else {
            Log.d("OLEG", "Idc,fu")
            filteredTracks = tracks
        }

        view?.showTracks(filteredTracks)
        playListManager.setPlayList(filteredTracks)
    }

    private fun filterTracks(tracks: List<Track>): List<Track> {
        return tracks.filter {
            it.trackName?.contains(searchString, true) ?: false ||
                    it.performerName?.contains(searchString, true) ?: false
        }
    }

    private fun filterTracksPoBolshe(tracks: List<Track>): List<Track> {
        return tracks.filter {
            var result = true

            if (filterMap!!["name"].toString() != "")
                result = result && it.trackName?.contains(    filterMap!!["name"].toString(), true) ?: false

            if (filterMap!!["author"].toString() != "")
                result = result && it.performerName?.contains(    filterMap!!["author"].toString(), true) ?: false

            if (filterMap!!["startYear"].toString() != "")
                result = result && it.dateAdded.toInt() > filterMap!!["startYear"]?.toInt() ?: Int.MAX_VALUE

            if (filterMap!!["endYear"].toString() != "")
                result = result && it.dateAdded.toInt() < filterMap!!["endYear"]?.toInt() ?: Int.MIN_VALUE

            if (filterMap!!["genres"].toString() != "")
                result = result && true

            result
        }
    }

    fun onSearchStringChanged(newSearchString: String?) {
        if (newSearchString != null) {
            searchString = newSearchString
            loadTracks()
        }
    }

    fun onFilterMapChanged(newFilterMap: Map<String, String>) {
            filterMap = newFilterMap
            loadTracks()
    }

    private fun onError(error: Throwable) {
        Log.d("FNP", "Error: $error")
    }
}