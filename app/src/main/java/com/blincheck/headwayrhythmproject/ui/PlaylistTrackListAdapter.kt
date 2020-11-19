package com.blincheck.headwayrhythmproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.ui.base.BaseViewAdapter

class PlaylistTrackListAdapter(private val itemClickListener: (Track) -> Unit) :
    BaseViewAdapter<Track>() {

    override fun getViewHolderByViewType(
        inflater: LayoutInflater,
        viewType: Int,
        parent: ViewGroup
    ) =
        PlaylistTrackViewHolder(
            inflater.inflate(R.layout.playlist_track_list_item, parent, false),
            itemClickListener
        )
}