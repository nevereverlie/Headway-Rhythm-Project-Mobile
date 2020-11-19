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
import com.blincheck.headwayrhythmproject.enity.AddToPlayListRequestBody
import com.blincheck.headwayrhythmproject.enity.Playlist
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.presenter.MainPresenter
import com.blincheck.headwayrhythmproject.presenter.ProfilePresenter
import com.blincheck.headwayrhythmproject.repository.PlaylistRepository
import com.blincheck.headwayrhythmproject.repository.WebService
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import com.blincheck.headwayrhythmproject.util.PlayListManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_to_playlist_dialog.view.*
import kotlinx.android.synthetic.main.filter_dialog.view.*

class MainActivity : BaseActivity<MainActivity, MainPresenter>(), FilterDialog.FilterDialogListener,
    AddToPlaylistDialog.AddToPlaylistDialogListener {

    override val layoutResId = R.layout.activity_main

    private val adapter = TrackListAdapter(::onItemClicked, ::onAddToPlaylistClicked)

    private val webService = WebService.getWebService()

    private val playListManager = PlayListManager()

    override val presenter = MainPresenter(playListManager)

    var d = FilterDialog()
    var ap = AddToPlaylistDialog()

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

    override fun onResume() {
        super.onResume()
        loadLists(getSharedPreferences("myPrefs", Context.MODE_PRIVATE).getInt("userId", -1))
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

    private fun onAddToPlaylistClicked(data: Track) {
        ap.track = data
        ap.show(supportFragmentManager, "addToPlaylist")
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


    fun loadLists(userId: Int) {
        val plRep = PlaylistRepository()
        plRep.getUserPlaylists(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onPlaylistsLoaded, ::onError)
    }

    fun onError(idk: Throwable) {
        Log.d("OLEH", "ERROR: " + idk.toString())
    }

    fun onErrorAddPlaylist(idk: Throwable) {
        Toast.makeText(this, "This track is already within selected playlist!", Toast.LENGTH_LONG)
            .show()
        Log.d("OLEH", "ERROR: " + idk.toString())
    }

    fun onPlaylistsLoaded(playlists: List<Playlist>) {
        ap.playlistsR = playlists
    }

    override fun onDialogPositiveClickFilter(dialog: DialogFragment?) {
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

    override fun onDialogPositiveClickAddToPlaylist(dialog: DialogFragment?) {
        dialog as AddToPlaylistDialog
        Log.d(
            "OLEH", "Token: !" + WebService.token + "!  " + dialog.playlistId.toString() +
                    "<-- Playlist and TrackId -->" + dialog.track!!.trackId +
                    " on top of that user id -->" + getSharedPreferences(
                "myPrefs",
                Context.MODE_PRIVATE
            ).getInt("userId", -1)
        )
        webService.addTrackToPlaylist(
            getSharedPreferences("myPrefs", Context.MODE_PRIVATE).getInt("userId", -1),
            WebService.token,
            AddToPlayListRequestBody(dialog.track!!.trackId, dialog.playlistId)

        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, ::onErrorAddPlaylist)
    }
}