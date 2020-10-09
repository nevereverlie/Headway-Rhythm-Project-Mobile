package com.blincheck.headwayrhythmproject.presenter

import android.util.Log
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.repository.TrackRepository
import com.blincheck.headwayrhythmproject.ui.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter : BasePresenter<MainActivity>() {

    private val trackRepository = TrackRepository()

    fun loadTracks() {
        trackRepository
            .getAllTracks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onTracksLoaded, ::onError)
    }

    private fun onTracksLoaded(tracks: List<Track>) {
        view?.showTracks(tracks)
    }

    private fun onError(error: Throwable) {
        Log.d("FNP", "Error: $error")
    }
}