package com.example.mindguard

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class TimerActivity : AppCompatActivity() {

    private lateinit var timerText: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button
    private lateinit var backButton: Button

    private var totalTime: Long = 0L
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        timerText = findViewById(R.id.timerText)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        resetButton = findViewById(R.id.resetButton)
        backButton = findViewById(R.id.button_back_timer)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.first)
        videoView.setVideoURI(videoUri)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.setOnPreparedListener {
            videoView.start()
        }

        videoView.setOnErrorListener { _, what, extra ->
            Log.e("VideoView", "Error: $what, Extra: $extra")
            true
        }

        val exerciseName = intent.getStringExtra("exercise_name") ?: "Ćwiczenie"
        totalTime = intent.getIntExtra("exercise_duration", 5 * 60) * 1000L

        title = exerciseName

        updateTimerDisplay(totalTime)

        startButton.setOnClickListener { startTimer() }
        stopButton.setOnClickListener { stopTimer() }
        resetButton.setOnClickListener { resetTimer() }
        backButton.setOnClickListener { finish() }

        Log.d("TimerActivity", "Uruchomiono ćwiczenie: $exerciseName, czas: $totalTime ms")
    }

    private fun startTimer() {
        timer?.cancel()

        timer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                totalTime = millisUntilFinished
                updateTimerDisplay(totalTime)
            }

            override fun onFinish() {
                timerText.text = "Czas minął!"
                resetButton.visibility = Button.VISIBLE
                startButton.visibility = Button.INVISIBLE
                stopButton.visibility = Button.INVISIBLE
            }
        }.start()

        startButton.visibility = Button.INVISIBLE
        stopButton.visibility = Button.VISIBLE
        resetButton.visibility = Button.INVISIBLE
    }

    private fun stopTimer() {
        timer?.cancel()
        startButton.visibility = Button.VISIBLE
        stopButton.visibility = Button.INVISIBLE
        resetButton.visibility = Button.VISIBLE
    }

    private fun resetTimer() {
        totalTime = 5 * 60 * 1000
        updateTimerDisplay(totalTime)

        startButton.visibility = Button.VISIBLE
        stopButton.visibility = Button.INVISIBLE
        resetButton.visibility = Button.INVISIBLE
    }

    private fun updateTimerDisplay(timeInMillis: Long) {
        val minutes = (timeInMillis / 1000) / 60
        val seconds = (timeInMillis / 1000) % 60
        timerText.text = String.format("%02d:%02d", minutes, seconds)
    }
}
