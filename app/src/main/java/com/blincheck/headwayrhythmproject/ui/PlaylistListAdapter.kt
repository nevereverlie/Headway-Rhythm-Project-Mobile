package com.blincheck.headwayrhythmproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.Playlist
import com.blincheck.headwayrhythmproject.ui.base.BaseViewAdapter

class PlaylistListAdapter(private val itemClickListener: (Playlist) -> Unit) :
    BaseViewAdapter<Playlist>() {

    override fun getViewHolderByViewType(
        inflater: LayoutInflater,
        viewType: Int,
        parent: ViewGroup
    ) =
        PlaylistViewHolder(
            inflater.inflate(R.layout.playlist_item, parent, false),
            itemClickListener
        )
}