package com.blincheck.headwayrhythmproject.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.Playlist
import com.blincheck.headwayrhythmproject.presenter.PlaylistsPresenter
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_playlists.*
import kotlinx.android.synthetic.main.prompts.view.*

class PlaylistsActivity : BaseActivity<PlaylistsActivity, PlaylistsPresenter>() {

    override val presenter = PlaylistsPresenter()

    override val layoutResId = R.layout.activity_playlists

    private val adapter = PlaylistListAdapter(::onItemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        initRecyclerView()

        addFab.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this).apply {
                val promptsView = LayoutInflater.from(context).inflate(R.layout.prompts, null)
                setView(promptsView)

                setCancelable(false)
                setPositiveButton("OK") { _: DialogInterface, _: Int ->
                    presenter.onCreatePlaylist(
                        promptsView.editTextDialogUserInput.text.toString(),
                        getSharedPreferences(
                            "myPrefs",
                            Context.MODE_PRIVATE
                        ).getInt("userId", -1)
                    )
                }

                setNegativeButton("Cancel") { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                }
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun initRecyclerView() {
        playlistsRecyclerView.adapter = adapter
        presenter.onLoadPlaylists(
            getSharedPreferences(
                "myPrefs",
                Context.MODE_PRIVATE
            ).getInt("userId", -1)
        )
    }

    fun showPlaylists(playlists: List<Playlist>) {
        adapter.updateList(playlists)
    }

    private fun onItemClicked(playlist: Playlist) {
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("playlistId", playlist.playlistId)
            putString("playlistName", playlist.playlistName)
            apply()
        }

        val intent = Intent(this, PlaylistActivity::class.java)
        startActivity(intent)
    }
}