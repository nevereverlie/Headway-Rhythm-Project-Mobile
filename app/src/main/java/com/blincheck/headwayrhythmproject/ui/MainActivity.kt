package com.blincheck.headwayrhythmproject.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Filter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.presenter.MainPresenter
import com.blincheck.headwayrhythmproject.repository.WebService
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import com.blincheck.headwayrhythmproject.util.PlayListManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.filter_dialog.view.*


class MainActivity : BaseActivity<MainActivity, MainPresenter>(),
    FilterDialog.FilterDialogListener {

    override val layoutResId = R.layout.activity_main

    private val adapter = TrackListAdapter(::onItemClicked)

    private val playListManager = PlayListManager()

    override val presenter = MainPresenter(playListManager)

    var d = FilterDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        profileImage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        libraryImage.setOnClickListener {
            val intent = Intent(this, PlaylistsActivity::class.java)
            startActivity(intent)
        }

        initRecyclerView()
        initMediaManager()

        WebService.token = getSharedPreferences(
            "myPrefs",
            Context.MODE_PRIVATE
        ).getString("token", "").toString()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.filter) {
            d.show(supportFragmentManager, "filter")
            return true
        }

        return super.onOptionsItemSelected(item)
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

    override fun onDialogPositiveClick(dialog: DialogFragment?) {
        dialog as FilterDialog

        val map: Map<String, String> = mapOf(
            "name" to dialog.dialogView.trackName?.text.toString(),
            "author" to dialog.dialogView.trackAuthor?.text.toString(),
            "startYear" to dialog.dialogView.startDate?.text.toString(),
            "endYear" to dialog.dialogView.endDate?.text.toString(),
            "genres" to dialog.dialogView.genres?.text.toString()
        )
        presenter.onFilterMapChanged(map)
        d.setFilterMap(map)
    }
}