package com.blincheck.headwayrhythmproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.presenter.MainPresenter
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import com.blincheck.headwayrhythmproject.util.PlayListManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainActivity, MainPresenter>() {

    override val layoutResId = R.layout.activity_main

    private val adapter = TrackListAdapter(::onItemClicked)

    private val playListManager = PlayListManager()

    override val presenter = MainPresenter(playListManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        profileImage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        initRecyclerView()
        initMediaManager()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.onSearchStringChanged(newText)
                return false
            }
        })
        return true
    }

    override fun onStop() {
        playListManager.pause()
        super.onStop()
    }

    private fun initRecyclerView() {
        track_list.adapter = adapter
        presenter.loadTracks()
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