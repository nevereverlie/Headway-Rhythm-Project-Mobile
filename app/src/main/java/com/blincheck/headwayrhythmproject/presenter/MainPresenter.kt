package com.blincheck.headwayrhythmproject.presenter

import android.util.Log
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.repository.TrackRepository
import com.blincheck.headwayrhythmproject.ui.MainActivity
import com.blincheck.headwayrhythmproject.util.PlayListManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter : BasePresenter<MainActivity>() {

    private val trackRepository = TrackRepository()
    private lateinit var playListManager: PlayListManager

    fun loadTracks(playListManager: PlayListManager) {
        this.playListManager = playListManager
        trackRepository
            .getAllTracks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onTracksLoaded, ::onError)
    }

    private fun onTracksLoaded(tracks: List<Track>) {
        view?.showTracks(tracks)
        playListManager.setPlayList(tracks)
    }

    private fun onError(error: Throwable) {
        Log.d("FNP", "Error: $error")
    }
}