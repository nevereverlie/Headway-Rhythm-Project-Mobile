package com.blincheck.headwayrhythmproject.ui

import android.view.View
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.ui.base.BaseViewAdapter
import kotlinx.android.synthetic.main.playlist_track_list_item.view.*

class PlaylistTrackViewHolder(view: View, itemClickListener: (Track) -> Unit) :
    BaseViewAdapter.ViewHolder<Track>(view) {

    init {
        itemView.setOnClickListener { itemClickListener.invoke(savedData!!) }
    }

    override fun setContent(data: Track) {
        itemView.nameTextView.text = data.trackName;
        itemView.artistTextView.text = data.performerName;
    }
}