package com.blincheck.headwayrhythmproject.repository

import com.blincheck.headwayrhythmproject.enity.CreatePlaylistBody

class PlaylistRepository {

    private val webService = WebService.getWebService()

    fun getUserPlaylists(userId: Int) = webService.getUserPlaylists(WebService.token, userId)

    fun createPlaylist(userId: Int, playlistName: String) =
        webService.createPlaylist(WebService.token, userId, CreatePlaylistBody(playlistName))

    fun getPlaylistTracks(userId: Int, playlistId: Int) =
        webService.getPlaylistTracks(WebService.token, userId, playlistId)
}