package com.blincheck.headwayrhythmproject.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.presenter.PlaylistPresenter
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import com.blincheck.headwayrhythmproject.util.PlayListManager
import kotlinx.android.synthetic.main.activity_playlist.*

class PlaylistActivity : BaseActivity<PlaylistActivity, PlaylistPresenter>() {

    private val playListManager = PlayListManager()

    override val presenter = PlaylistPresenter(playListManager)

    override val layoutResId = R.layout.activity_playlist

    private val adapter = PlaylistTrackListAdapter(::onItemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playlistName = getSharedPreferences(
            "myPrefs",
            Context.MODE_PRIVATE
        ).getString("playlistName", "Unknown")

        playlistNameTextView.text = playlistName

        initRecyclerView()
        initMediaManager()
    }

    fun showTracks(tracks: List<Track>) {
        adapter.updateList(tracks)
    }

    private fun initRecyclerView() {
        tracksRecyclerView.adapter = adapter

        val userId = getSharedPreferences(
            "myPrefs",
            Context.MODE_PRIVATE
        ).getInt("userId", -1)

        val playlistId = getSharedPreferences(
            "myPrefs",
            Context.MODE_PRIVATE
        ).getInt("playlistId", -1)

        presenter.onLoadPlaylistTracks(userId, playlistId)
    }

    override fun onStop() {
        playListManager.pause()
        super.onStop()
    }

    private fun initMediaManager() {
        playListManager.setMainActivity(this)
    }

    private fun onItemClicked(data: Track) {
        playListManager.start(data)
    }

    fun playBtnClick(view: View) {
        playListManager.startOrPause()
    }

    fun prevTrackBtnClick(view: View) {
        playListManager.prevTrackBtnClick()
    }

    fun nextTrackBtnClick(view: View) {
        playListManager.nextTrackBtnClick()
    }
}