package com.example.voicesystemnewversion

import android.content.Context
import android.media.MediaPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
                1 -> R.raw.ten
                2 -> R.raw.twenty
                3 -> R.raw.thirty
                4 -> R.raw.fourty
                5 -> R.raw.fifty
                6 -> R.raw.sixty
                7 -> R.raw.seventy
                8 -> R.raw.eighty
                9 -> R.raw.ninety
                else -> 0
            }
            return dozens
        }

        private fun checkDozen(course: String): Boolean {
            var dozen = course.toInt() % 100
            return dozen in 11..19
        }

        private fun chooseOneDozen(course: String): Int {
                var result = when (course.toInt() % 100) {
                    11 -> R.raw.eleven
                    12 -> R.raw.twelve
                    13 -> R.raw.thirteen
                    14 -> R.raw.fourteen
                    15 -> R.raw.fifteen
                    16 -> R.raw.sixteen
                    17 -> R.raw.seventeen
                    18 -> R.raw.eighteen
                    19 -> R.raw.nineteen
                    else -> 0
            }
            return result
        }

        private fun chooseUnitVoiceOption(course: String): Int {
            val unit = when (course.toInt() % 10) {
                1 -> R.raw.one
                2 -> R.raw.two
                3 -> R.raw.three
                4 -> R.raw.four
                5 -> R.raw.five
                6 -> R.raw.six
                7 -> R.raw.seven
                8 -> R.raw.eight
                9 -> R.raw.nine
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
            if (checkDozen(course)) {
                val oneDozen = chooseOneDozen(course)
                list.add(oneDozen)
            } else {
            val dozen = chooseDozensVoiceOption(course)
            val unit = chooseUnitVoiceOption(course)
            list.add(R.raw.paused)
            list.add(dozen)
            list.add(R.raw.paused)
            list.add(unit)
            }
            return list
        }

        fun play(
            context: Context,
            item: Int
        ) = runBlocking {
            launch {
                val mediaPlayer = MediaPlayer.create(context, item)
                delay(400)
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