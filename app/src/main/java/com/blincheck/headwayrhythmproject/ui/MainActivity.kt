package com.blincheck.headwayrhythmproject.ui

import android.os.Bundle
import android.view.View
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.presenter.MainPresenter
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import com.blincheck.headwayrhythmproject.util.MediaManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainActivity, MainPresenter>() {

    override val presenter = MainPresenter()

    override val layoutResId = R.layout.activity_main

    private val adapter = TrackListAdapter(::onItemClicked)

    private val player = MediaManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        initMediaManager()
    }

    private fun initRecyclerView() {
        track_list.adapter = adapter
        presenter.loadTracks()
    }

    private fun initMediaManager() {
        player.setMainActivity(this)
    }

    fun showTracks(tracks: List<Track>) {
        adapter.updateList(tracks)
    }

    private fun onItemClicked(data: Track) {
        player.start(data.url)
    }

    fun playBtnClick(view: View) {
        player.playBtnClick()
    }

    fun prevTrackBtnClick(view: View) {
        player.prevTrackBtnClick()
    }

    fun nextTrackBtnClick(view: View) {
        player.nextTrackBtnClick()
    }
}