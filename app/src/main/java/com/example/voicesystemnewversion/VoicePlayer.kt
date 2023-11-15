package com.example.voicesystemnewversion

import android.content.Context
import android.media.MediaPlayer
import androidx.core.os.postDelayed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.logging.Handler

class VoicePlayer {

    companion object {


        private fun chooseHundredVoiceOption(course: String): Int {
            val hundred = when (course.toInt() / 100) {
                1 -> R.raw.onehundred
                2 -> R.raw.twohundred
                3 -> R.raw.threehundred
                else -> 0

            }
            return hundred
        }

        private fun chooseDozensVoiceOption(course: String): Int {
            val dozens = when (course.toInt() / 10 % 10) {
                4 -> R.raw.forty
                5 -> R.raw.fifty
                6 -> R.raw.sixty
                7 -> R.raw.seventeen
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
            if (course.length > 2) {
                val hundred = chooseHundredVoiceOption(course)
                list.add(hundred)
            }
            val dozens = chooseDozensVoiceOption(course)
            val unit = chooseUnitVoiceOption(course)
            list.add(R.raw.paused)
            list.add(dozens)
            list.add(R.raw.paused)
            list.add(unit)
            return list
        }

        fun play(
            context: Context,
            item: Int
        ) = runBlocking {
            launch {
                val mediaPlayer = MediaPlayer.create(context, item)
                delay(300)
                mediaPlayer.start()
            }

        }

    }
}

fun stop() {

}

object Repeat {
    private val handler = android.os.Handler()
    fun play(
        list: ArrayList<Int>,
        context: Context
    ) {
        for (i in list.indices) {
            val item = list[i]
            val mediaPlayer = MediaPlayer.create(context, item)

            handler.postDelayed({
                mediaPlayer.start()
            }, 5000)
        }
    }
}