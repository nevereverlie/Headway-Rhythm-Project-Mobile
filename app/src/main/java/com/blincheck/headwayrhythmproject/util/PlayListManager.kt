package com.blincheck.headwayrhythmproject.util

import com.blincheck.headwayrhythmproject.ui.MainActivity
import com.blincheck.headwayrhythmproject.enity.Track

class PlayListManager {
    private val player = MediaManager()
    private var playlist: List<Track>? = null
    private var trackIndex: Int = -1

    fun start(track: Track){
        if (playlist != null) {
            for (i in 1..playlist?.size!!) {
                if (playlist!![i - 1].trackId == track.trackId) {
                    start(i - 1)
                    return
                }
            }
        }
    }

    fun start(trackIndex: Int) {
        if (this.playlist?.size!! > trackIndex) {
            this.trackIndex = trackIndex
            player.start(this.playlist!![trackIndex])
        }
    }

    fun start() {
        if (this.playlist?.size!! > 0) {
            trackIndex = 0
            player.start(this.playlist!![0])
        }
    }

    fun setPlayList(playlist: List<Track>) {
        player.pause()
        this.playlist = playlist
        this.trackIndex = 0
    }

    fun setMainActivity(mainActivity: MainActivity) {
        player.setMainActivity(mainActivity)
    }

    fun startOrPause() {
        player.startOrPause()
    }

    fun prevTrackBtnClick() {
        if (this.playlist?.size!! > 0) {
            trackIndex = (trackIndex - 1) % playlist!!.size
            player.start(this.playlist!![trackIndex])
        }
    }

    fun nextTrackBtnClick() {
        if (this.playlist?.size!! > 0) {
            trackIndex = (playlist!!.size + trackIndex + 1) % playlist!!.size
            player.start(this.playlist!![trackIndex])
        }
    }
}