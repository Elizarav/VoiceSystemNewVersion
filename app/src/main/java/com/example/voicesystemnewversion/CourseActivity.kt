package com.example.voicesystemnewversion

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CourseActivity : AppCompatActivity() {

    private lateinit var courseDisplayTV: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        courseDisplayTV = findViewById(R.id.courseDisplayTV)

        val course = intent.getStringExtra("Key_course")
        courseDisplayTV.text = course.toString()
        val list = VoicePlayer.createPlayList(course.toString())
        for (i in list.indices) {
            VoicePlayer.play(this, list[i])
        }
        finish()
    }
}