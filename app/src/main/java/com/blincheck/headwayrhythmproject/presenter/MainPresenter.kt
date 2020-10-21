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

    fun loadTracks() {
        trackRepository
            .getAllTracks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onTracksLoaded, ::onError)
    }

    private fun onTracksLoaded(tracks: List<Track>) {
        val filteredTracks = if (searchString.isEmpty()) tracks else filterTracks(tracks)
        view?.showTracks(filteredTracks)
        playListManager.setPlayList(filteredTracks)
    }

    private fun filterTracks(tracks: List<Track>): List<Track> {
        return tracks.filter {
            it.trackName?.contains(searchString, true) ?: false ||
                    it.performerName?.contains(searchString, true) ?: false
        }
    }

    fun onSearchStringChanged(newSearchString: String?) {
        if (newSearchString != null) {
            searchString = newSearchString
            loadTracks()
        }
    }

    private fun onError(error: Throwable) {
        Log.d("FNP", "Error: $error")
    }
}