package com.blincheck.headwayrhythmproject.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.Playlist
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.repository.PlaylistRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_to_playlist_dialog.view.*


class AddToPlaylistDialog : DialogFragment() {
    lateinit var dialogView : View
    var track: Track? = null
    var playlistId: Int = 0
    var arraySpinner: ArrayList<String>? = null
    var playlistsR: List<Playlist>? = null

    @Override
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        dialogView = inflater.inflate(R.layout.add_to_playlist_dialog, null)

        arraySpinner = arrayListOf<String>()
        Log.d("OLEH", "PL " + playlistsR!!.toString())

        for (pl in playlistsR!!) {
            Log.d("OLEH", "F " + pl.toString())

            arraySpinner!!.add(pl.playlistName)
        }
        Log.d("OLEH", "ARR " + arraySpinner!!.toString())

        val spiner: Spinner = dialogView.findViewById(R.id.playlistsSpinner) as Spinner
        val adp1 = ArrayAdapter<String>(
            this.requireContext(), android.R.layout.simple_spinner_item, arraySpinner!!
        )
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spiner.setAdapter(adp1)

        dialogView.playlistsSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = playlistsR!!.elementAt(position)
                playlistId = selectedItem.playlistId
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }// to close the onItemSelected

        builder.setView(dialogView).setPositiveButton("Add to playlist"
        ) { dialog, which ->
            mListener!!.onDialogPositiveClickAddToPlaylist(this)
        }

        return builder.create()
    }

    interface AddToPlaylistDialogListener {
        fun onDialogPositiveClickAddToPlaylist(dialog: DialogFragment?)
    }

    var mListener: AddToPlaylistDialogListener? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mListener = try {
            activity as AddToPlaylistDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " must implement NoticeDialogListener"
            )
        }
    }
}
