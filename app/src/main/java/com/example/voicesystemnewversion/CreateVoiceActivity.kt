package com.example.voicesystemnewversion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CreateVoiceActivity : AppCompatActivity() {

    private lateinit var courseDisplayTV: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        courseDisplayTV = findViewById(R.id.courseDisplayTV)

        val course = intent.getStringExtra("Key_course")
        val bearing = intent.getStringExtra("Key_bearing")
        if (VoicePlayer.isTime(course.toString())) {
            val traverseTimeList = VoicePlayer.createTimePlayList(course.toString())
            for (i in traverseTimeList.indices) {
                VoicePlayer.play(this, traverseTimeList[i])
            }
        } else {
            courseDisplayTV.text = course.toString()
            val list = VoicePlayer.createCoursePlayList(course.toString())
            for (i in list.indices) {
                VoicePlayer.play(this, list[i])
            }
        }
//        VoicePlayer.play(this, R.raw.paused)
        val bearingList = VoicePlayer.createBearingPlayList(bearing.toString())
//        VoicePlayer.play(this, R.raw.paused)
        for (i in bearingList.indices) {
            VoicePlayer.play(this, bearingList[i])
        }
        finish()
    }
}