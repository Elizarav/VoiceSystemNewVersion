package com.example.voicesystemnewversion

import android.content.Context
import android.media.MediaPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class VoicePlayer {

    companion object {
        private fun chooseHundredVoiceOption(course: String): Int {
            val hundred = when (course.toInt() / 100) {
                1 -> R.raw.onehundred
                2 -> R.raw.twohundred
                3 -> R.raw.threehundred
                else -> R.raw.paused

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
                else -> R.raw.pausedsmallxxx
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
                else -> R.raw.pausedsmallxxx
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
                else -> R.raw.pausedsmallxxx
            }
            return unit
        }

        fun createCoursePlayList(course: String): ArrayList<Int> {
            val list = ArrayList<Int>()
            list.add(R.raw.course)
//            list.add(R.raw.paused)
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
//                list.add(R.raw.paused)
                list.add(dozen)
//                list.add(R.raw.paused)
                list.add(unit)

            }
            return list
        }

        fun createBearingPlayList(bearing: String): ArrayList<Int> {
            val list = ArrayList<Int>()
            list.add(R.raw.mpr)
//            list.add(R.raw.paused)
            list.add(R.raw.paused)
            if (bearing.length > 2) {
                val hundred = chooseHundredVoiceOption(bearing)
                list.add(hundred)
            }
            if (checkDozen(bearing)) {
                val oneDozen = chooseOneDozen(bearing)
                list.add(oneDozen)
            } else {
                val dozen = chooseDozensVoiceOption(bearing)
                val unit = chooseUnitVoiceOption(bearing)
//                list.add(R.raw.paused)
                list.add(dozen)
//                list.add(R.raw.paused)
                list.add(unit)
            }
            return list
        }

        private fun convertStringTimeWithoutDot(time: String): String {
            val builder = StringBuilder()
            val charTime = time.toCharArray()
            for (i in charTime.indices) {
                if (charTime[i] == '.') continue
                builder.append(charTime[i])
            }
            return builder.toString()
        }

        fun isTime(current: String): Boolean {
            val cars = current.toCharArray()
            for (i in cars.indices) {
                if (cars[i] == '.') {
                    return true
                    break
                }
            }
            return false
        }

        fun createTimePlayList(time: String): ArrayList<Int> {
            val list = ArrayList<Int>()

            var timeString = convertStringTimeWithoutDot(time)
            val result = when (timeString.toInt() / 100) {
                1 -> R.raw.oneminute
                2 -> R.raw.twominute
                3 -> R.raw.threeminute
                4 -> R.raw.fourminute
                5 -> R.raw.fiveminute
                else -> R.raw.pausedsmallxxx
            }
            list.add(result)
            if (checkDozen(timeString)) {
                val oneDozen = chooseOneDozen(timeString)
                list.add(oneDozen)
                list.add(R.raw.second)
            } else {
                val dozen = chooseDozensVoiceOption(timeString)
                if (result != R.raw.pausedsmall) {
                    list.add(R.raw.pausedsmall)
                }
                list.add(dozen)
//                list.add(R.raw.paused)
                val unit = chooseUnitVoiceOption(timeString)
                if (unit != R.raw.pausedsmall) {
                    list.add(unit)
//                    list.add(R.raw.paused)
                }

                val ending = when (unit) {
                    2, 3, 4 -> R.raw.secondtwo
                    0, 5, 6, 7, 8, 9 -> R.raw.second
                    else -> R.raw.onesecond
                }
                list.add(ending)
            }
            return list
        }

        fun play(
            context: Context,
            item: Int
        ) = runBlocking {
            launch {
                val mediaPlayer = MediaPlayer.create(context, item)
                delay(800)
                mediaPlayer.start()
            }
        }

    }
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