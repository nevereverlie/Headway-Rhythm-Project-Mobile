package com.blincheck.headwayrhythmproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.Track
import com.blincheck.headwayrhythmproject.ui.base.BaseViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class TrackListAdapter(private val itemClickListener: (Track) -> Unit,
                       private val itemClickListener2: (Track) -> Unit) : BaseViewAdapter<Track>() {

    override fun getViewHolderByViewType(
        inflater: LayoutInflater,
        viewType: Int,
        parent: ViewGroup
    ) =
        TrackViewHolder(
            inflater.inflate(R.layout.track_list_item, parent, false),
            itemClickListener, itemClickListener2
        )
}