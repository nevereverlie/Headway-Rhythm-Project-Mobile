package com.blincheck.headwayrhythmproject.presenter

import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.repository.PlaylistRepository
import com.blincheck.headwayrhythmproject.ui.PlaylistActivity
import com.blincheck.headwayrhythmproject.util.PlayListManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PlaylistPresenter(private var playListManager: PlayListManager) : BasePresenter<PlaylistActivity>() {

    private val playlistRepository = PlaylistRepository()

    fun onLoadPlaylistTracks(userId: Int, playlistId: Int) {
        playlistRepository.getPlaylistTracks(userId, playlistId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onPlaylistTracksLoaded, ::onError)
    }

    private fun onPlaylistTracksLoaded(tracks: List<Track>) {
        view?.showTracks(tracks)
        playListManager.setPlayList(tracks)
    }
}