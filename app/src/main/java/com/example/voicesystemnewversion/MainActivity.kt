package com.example.voicesystemnewversion

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    companion object {
        const val TIME_FACTOR = 1000
        const val TIME_FLY = 900000
    }

    private lateinit var chronometer: Chronometer

    private lateinit var numberTV: TextView
    private lateinit var courseTV: TextView
    private lateinit var courseTV2: TextView
    private lateinit var courseTVtr: TextView
    private lateinit var courseTV3: TextView
    private lateinit var courseTV4: TextView

    private lateinit var timeTV: TextView
    private lateinit var timeTV2: TextView
    private lateinit var timeTV3: TextView
    private lateinit var timeTVtr: TextView
    private lateinit var timeTV4: TextView

    private lateinit var bearingTV: TextView
    private lateinit var bearingTV2: TextView
    private lateinit var bearingTVtr: TextView
    private lateinit var bearingTV3: TextView
    private lateinit var bearingTV4: TextView

    private lateinit var startBTN: Button
    private lateinit var stopBTN: Button

    private lateinit var lookTimeTV: TextView
    private lateinit var numberOfFlightsTV: TextView
    private lateinit var countOfFlightsTV: TextView


    private val timeList = ArrayList<Long>()
    private var timeListAfterConvertMil = ArrayList<Long>()
    private val timeListForTime = ArrayList<Long>()

    private var originalTimeList = ArrayList<String>()
    private var originalCourseList = ArrayList<String>()
    private var originalBearingList = ArrayList<String>()

    var currentCourse = "0"
    private var countFlight = 0

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }


    override fun onResume() {
        super.onResume()

        originalCourseList = mutableListOf(
            courseTV.text.toString(),
            courseTV2.text.toString(),
            courseTVtr.text.toString(),
            courseTV3.text.toString(),
            courseTV4.text.toString(),
            courseTV.text.toString()
        ) as ArrayList<String>

        originalTimeList = mutableListOf(
            timeTV.text.toString(),
            timeTV2.text.toString(),
            timeTVtr.text.toString(),
            timeTV3.text.toString(),
            timeTV4.text.toString(),
            "12.00"
        ) as ArrayList<String>

        originalBearingList = mutableListOf(
            bearingTV2.text.toString(),
            bearingTVtr.text.toString(),
            bearingTV3.text.toString(),
            bearingTV4.text.toString()
        ) as ArrayList<String>

        onClick()
    }


    private fun init() {
        numberTV = findViewById(R.id.numberTV)
        courseTV = findViewById(R.id.courseTV)
        courseTV2 = findViewById(R.id.courseTV2)
        courseTVtr = findViewById(R.id.courseTVtr)
        courseTV3 = findViewById(R.id.courseTV3)
        courseTV4 = findViewById(R.id.courseTV4)

        timeTV = findViewById(R.id.timeTV)
        timeTV2 = findViewById(R.id.timeTV2)
        timeTV3 = findViewById(R.id.timeTV3)
        timeTVtr = findViewById(R.id.timeTVtr)
        timeTV4 = findViewById(R.id.timeTV4)

        bearingTV = findViewById(R.id.bearingTV)
        bearingTV2 = findViewById(R.id.bearingTV2)
        bearingTVtr = findViewById(R.id.bearingTVtr)
        bearingTV3 = findViewById(R.id.bearingTV3)
        bearingTV4 = findViewById(R.id.bearingTV4)

        startBTN = findViewById(R.id.startTimeBTN)
        stopBTN = findViewById(R.id.stopTimeBTN)
        lookTimeTV = findViewById(R.id.lookTimeTV)
        numberOfFlightsTV = findViewById(R.id.numberOfFlightsTV)
        countOfFlightsTV = findViewById(R.id.countOfFlightsTV)

        chronometer = findViewById(R.id.chronometer)
    }


    private fun onClick(): String {
        var tempCourse = originalCourseList[0]
        var resultCourse = "0"

        removeAndAddElementTimeList()

        startBTN.setOnClickListener {

            countFlight++
            countOfFlightsTV.text = countFlight.toString()
            lookTimeTV.text = tempCourse
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()

            timeListAfterConvertMil = convertToTimeMillis(originalTimeList)
            val timeListConvert = timeListAfterConvertMil
            val currentTempList = timeList
            val courseList = originalCourseList
            val bearingList = originalBearingList

            val traverseCurrentTime = timeListAfterConvertMil[2]
            val traverseTime = timeTV3.text.toString()
            var equalsCourseForTraverse = courseList[3]
//---------------------------------------------------------------------------------------
            RepeatHelper.repeatDelayed(2000) {
                addToList(
                    timeList,
                    SystemClock.elapsedRealtime() - chronometer.base
                )
            }

            RepeatHelper.repeatSpecifyTheCourseSpecifyTheCourse(
                2000
            ) {
                tempCourse = specifyTheCourse(
                    currentTempList,
                    timeListConvert,
                    courseList,
                    bearingList,
                    equalsCourseForTraverse,
                    traverseCurrentTime,
                    traverseTime
                )
            }
        }
        resultCourse = tempCourse
//-------------------------------------------------------------------------------------
        stopBTN.setOnClickListener {
            chronometer.stop()
            chronometer.base = SystemClock.elapsedRealtime()
            RepeatHelper.stopHandler()
            lookTimeTV.text = ""

        }
        return resultCourse
    }

    private fun removeAndAddElementTimeList() {
        val originalTimeListValue = getTimeRemains()
        originalTimeList.removeAt(5)
        originalTimeList.add(5, originalTimeListValue)
    }

    private fun convertToTimeMillis(originalTimeList: ArrayList<String>): ArrayList<Long> {
        val list = ArrayList<Long>()
        var result: Long = 0
        var minutes = ""
        var seconds = ""
        for (i in originalTimeList.indices) {
            var check = true
            result = 0
            minutes = ""
            seconds = ""
            val timeListToChar = originalTimeList[i].toCharArray()
            for (j in timeListToChar.indices) {
                if (timeListToChar[j] == '.') {
                    break
                }
                minutes += "${timeListToChar[j]}"
            }
            for (j in timeListToChar.indices) {
                if (!check) {
                    seconds += "${timeListToChar[j]}"
                }
                if (timeListToChar[j] == '.') {
                    check = false
                }
            }
            result = ((minutes.toInt() * 60) + seconds.toInt()).toLong()
            list.add(result * TIME_FACTOR)
        }
        var afterTR = list[2] + list[3]
        var after3 = afterTR + list[4]
        list.removeAt(3)
        list.add(3, afterTR)
        list.removeAt(4)
        list.add(4, after3)
        return list
    }

    private fun getTimeRemains(): String {
        val listTime = this.convertToTimeMillis(originalTimeList)
        var sum = 0L
        for (i in listTime.indices) {
            if (i > 1) {
                if (listTime[i] == listTime[listTime.size - 1]) break
                sum += i
            }
        }
        val result = TIME_FLY - sum
        val minutes = (result / 1000 / 60 % 60).toInt()
        val seconds = (result / 1000 % 60).toInt()
        val zero = "0"
        val customText = if (seconds < 10) {
            "$minutes.${zero + seconds}"
        } else {
            "$minutes.$seconds"
        }
        return customText
    }

    private fun addToList(list: ArrayList<Long>, currentTime: Long) {
        timeList.add(currentTime)
    }

    private fun specifyTheTraverse(
        timeListForTime: ArrayList<Long>,
        traverseCurrentTime: Long,
        traverseTime: String
    ) {
        var item = timeListForTime
        if (traverseCurrentTime - 3000 <= timeListForTime[0]) {
            val intent = Intent(this, CreateTimeVoiceActivity::class.java)
            intent.putExtra("Key_time", traverseTime)
            startActivity(intent)
        } else {
            timeListForTime.clear()
        }
    }

    private fun specifyTheCourse(
        timeList: ArrayList<Long>,
        timeListAfterConvertMil: ArrayList<Long>,
        originalCourseList: ArrayList<String>,
        bearingList: ArrayList<String>,
        equalsCourseForTraverse: String,
        traverseCurrentTime: Long,
        traverseTime: String
        ): String {
        var resultCourse = originalCourseList[0]
        var current = resultCourse
        var count = 0

        if (timeListAfterConvertMil.isNotEmpty()) {
            if (timeListAfterConvertMil[0] <= timeList[0]) {

                originalCourseList.removeAt(0)
                if (originalCourseList.isEmpty()) {
                    lookTimeTV.text = "256"
                } else {
                    count = 0
                    lookTimeTV.text = originalCourseList[0]
                    count++
                    if (count == 1) {
                        resultCourse = lookTimeTV.text.toString()
                        if (lookTimeTV.text == equalsCourseForTraverse
                            && timeListAfterConvertMil[0] == traverseCurrentTime) {
                            resultCourse = traverseTime
                        }

                    }
                }
                timeList.clear()
                timeListAfterConvertMil.removeAt(0)
            } else {
                timeList.clear()
            }
        }
        if (resultCourse != current) {
            val intent = Intent(this, CreateVoiceActivity::class.java)
            intent.putExtra("Key_course", resultCourse)
            intent.putExtra("Key_bearing", bearingList[0])
            startActivity(intent)
            bearingList.removeAt(0)
        }
        return resultCourse
    }
}

object RepeatHelper {

    val handler = Handler()
    fun repeatDelayed(delay: Long, todo: () -> Unit) {
        var list = ArrayList<Long>()

        handler.postDelayed(object : Runnable {
            override fun run() {
                todo()
                handler.postDelayed(this, delay)
            }
        }, delay)
    }

    fun repeatSpecifyTheCourseSpecifyTheCourse(
        delay: Long,
        todo: () -> Unit
    ) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                todo()
                handler.postDelayed(this, delay)
            }
        }, delay)
    }

    fun stopHandler() {
        handler.removeCallbacksAndMessages(null);
    }
}
