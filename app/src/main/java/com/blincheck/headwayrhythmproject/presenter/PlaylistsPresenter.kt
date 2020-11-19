package com.blincheck.headwayrhythmproject.presenter

import android.util.Log
import com.blincheck.headwayrhythmproject.enity.Playlist
import com.blincheck.headwayrhythmproject.repository.PlaylistRepository
import com.blincheck.headwayrhythmproject.ui.PlaylistsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PlaylistsPresenter : BasePresenter<PlaylistsActivity>() {

    private val playlistRepository = PlaylistRepository()

    private var currentUserId = -1

    fun onLoadPlaylists(userId: Int) {
        Log.d("FNP", "userId: $userId")

        playlistRepository
            .getUserPlaylists(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onPlaylistsLoaded, ::onError)
    }

    private fun onPlaylistsLoaded(playlists: List<Playlist>) {
        view?.showPlaylists(playlists)
    }

    fun onCreatePlaylist(playlistName: String, userId: Int) {
        currentUserId = userId

        playlistRepository.createPlaylist(userId, playlistName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onPlaylistCreated, ::onError)
    }

    private fun onPlaylistCreated() {
        onLoadPlaylists(currentUserId)
    }
}