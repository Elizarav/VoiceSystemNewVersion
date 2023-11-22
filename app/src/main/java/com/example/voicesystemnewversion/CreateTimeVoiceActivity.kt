package com.example.voicesystemnewversion

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class CreateTimeVoiceActivity : AppCompatActivity() {

    private lateinit var timeDisplayTV: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_time_voice)

        timeDisplayTV = findViewById(R.id.timeDisplayTV)

        val traverseTime = intent.getStringExtra("Key_time")
        val traverseTimeList = VoicePlayer.createTimePlayList(traverseTime.toString())
        for (i in traverseTimeList.indices) {
            VoicePlayer.play(this, traverseTimeList[i])
        }
        finish()
    }
}