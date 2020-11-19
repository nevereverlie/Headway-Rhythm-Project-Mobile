package com.blincheck.headwayrhythmproject.ui

import android.view.View
import com.blincheck.headwayrhythmproject.enity.Playlist
import com.blincheck.headwayrhythmproject.ui.base.BaseViewAdapter
import kotlinx.android.synthetic.main.playlist_item.view.*

class PlaylistViewHolder(view: View, itemClickListener: (Playlist) -> Unit) :
    BaseViewAdapter.ViewHolder<Playlist>(view) {

    init {
        itemView.setOnClickListener { itemClickListener.invoke(savedData!!) }
    }

    override fun setContent(data: Playlist) {
        itemView.playlistNameText.text = data.playlistName
    }
}