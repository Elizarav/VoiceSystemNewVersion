package com.example.voicesystemnewversion

import android.content.Context
import android.media.MediaPlayer
import androidx.core.os.postDelayed
import java.util.logging.Handler

class VoicePlayer {

    companion object {


        private fun chooseHundredVoiceOption(course: String): Int {
            val hundred = when (course.toInt() / 100) {
                3 -> R.raw.threehundred
                else -> 0

            }
            return hundred
        }

        private fun chooseDozensVoiceOption(course: String): Int {
            val dozens = when (course.toInt() / 10 % 10) {
                4 -> R.raw.forty
                else -> 0
            }
            return dozens
        }

        private fun chooseUnitVoiceOption(course: String): Int {
            val unit = when (course.toInt() % 10) {
                6 -> R.raw.six
                else -> 0
            }
            return unit
        }

        fun createPlayList(course: String): ArrayList<Int> {
            val list = ArrayList<Int>()
            val hundred = chooseHundredVoiceOption(course)
            val dozens = chooseDozensVoiceOption(course)
            val unit = chooseUnitVoiceOption(course)
            val pause = R.raw.paused
            list.add(hundred)
            list.add(pause)
            list.add(dozens)
            list.add(pause)
            list.add(unit)
            return list
        }

        fun play(
            context: Context,
            list: ArrayList<Int>
        ) {
            var mediaPlayer: MediaPlayer

            if (list.isEmpty()) {
                return
            }
            val item = list[0]
            mediaPlayer = MediaPlayer.create(context, item)
            mediaPlayer.start()
            Thread.sleep(300)
        }
    }
}

//    object Repeat {
//        private val handler = android.os.Handler()
//        fun play(
//            course: String,
//            context: Context
//        ) {
//            val list = VoicePlayer.createPlayList(course)
//            for (i in list.indices) {
//                val item = list[i]
//                val mediaPlayer = MediaPlayer.create(context, item)
//
//                handler.postDelayed({
//                    mediaPlayer.start()
//                }, 1000)
//            }
//        }
//    }