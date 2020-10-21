package com.blincheck.headwayrhythmproject.ui

import android.os.Bundle
import android.view.View
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.presenter.MainPresenter
import com.blincheck.headwayrhythmproject.repository.WebService
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import com.blincheck.headwayrhythmproject.util.PlayListManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainActivity, MainPresenter>() {

    override val presenter = MainPresenter()

    override val layoutResId = R.layout.activity_main

    private val adapter = TrackListAdapter(::onItemClicked)

    private val playListManager = PlayListManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        initMediaManager()
    }

    private fun initRecyclerView() {
        track_list.adapter = adapter
        presenter.loadTracks(playListManager)
    }

    private fun initMediaManager() {
        playListManager.setMainActivity(this)
    }

    fun showTracks(tracks: List<Track>) {
        adapter.updateList(tracks)
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