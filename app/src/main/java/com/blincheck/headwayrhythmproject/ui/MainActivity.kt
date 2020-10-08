package com.blincheck.headwayrhythmproject.ui

import android.os.Bundle
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.presenter.MainPresenter
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainActivity, MainPresenter>() {

    override val presenter = MainPresenter()

    override val layoutResId = R.layout.activity_main

    private val adapter = TrackListAdapter(::onItemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        track_list.adapter = adapter
        presenter.loadTracks()
    }

    fun showTracks(tracks: List<Track>) {
        adapter.updateList(tracks)
    }

    private fun onItemClicked(data: Track) {

    }
}