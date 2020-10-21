package com.blincheck.headwayrhythmproject.util

import com.blincheck.headwayrhythmproject.ui.MainActivity

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.Message
import android.widget.SeekBar
import com.blincheck.headwayrhythmproject.enity.Track
import kotlinx.android.synthetic.main.activity_main.*

class MediaManager {
    private var mp: MediaPlayer
    private var totalTime: Int = 0
    private var activity: MainActivity? = null

    init {
        mp = MediaPlayer()
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    fun start(track: Track){
        mp.reset()

        mp.setDataSource(track.url)
        mp.prepareAsync()

        activity?.trackNameLabel?.text = track.trackName
    }

    fun pause(){
        mp.pause()
    }

    fun resume(){
        mp.start()
    }

    fun setMainActivity(mainActivity: MainActivity) {
        if (activity == null) {
            activity = mainActivity

            mp.setOnPreparedListener {
                if (!mp.isPlaying)
                {
                    totalTime = mp.duration
                    activity?.positionBar?.max = totalTime
                    mp.start()
                }
            }


            // Position Bar
            activity?.positionBar?.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        if (fromUser) {
                            mp.seekTo(progress)
                        }
                    }
                    override fun onStartTrackingTouch(p0: SeekBar?) {
                    }
                    override fun onStopTrackingTouch(p0: SeekBar?) {
                    }
                }
            )

            @SuppressLint("HandlerLeak")
            var handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    var currentPosition = msg.what

                    // Update positionBar
                    activity?.positionBar?.progress = currentPosition

                    // Update Labels
                    var elapsedTime = createTimeLabel(currentPosition)
                    activity?.elapsedTimeLabel?.text = elapsedTime

                    var remainingTime = createTimeLabel(totalTime - currentPosition)
                    activity?.remainingTimeLabel?.text = "-$remainingTime"
                }
            }

            val t = Thread(
                {
                    while (mp != null)
                    {
                        try
                        {
                            var msg = Message()
                            msg.what = mp.currentPosition
                            handler.sendMessage(msg)
                            Thread.sleep(1000)
                        } catch (e: InterruptedException)
                        {
                        }
                    }
                })
            t.start()
        }
    }

    fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    fun startOrPause() {
        if (mp.isPlaying) {
            mp.pause()

        } else {
            mp.start()
        }
    }

    fun isPlaying(): Boolean {
        return mp.isPlaying
    }
}