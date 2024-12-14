package com.bhat.stopwatch

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private var seconds = 0
    private var running = false
    private var wasRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            savedInstanceState.getInt("seconds")
            savedInstanceState.getBoolean("running")
            savedInstanceState.getBoolean("wasRunning")
        }
        runTimer()
    }

    fun onStart(view: View?) {
        running = true
    }

    fun onStop(view: View?) {
        running = false
    }

    fun onReset(view: View?) {
        running = false
        seconds = 0
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
        outState.putBoolean("wasRunning", wasRunning)
    }

    private fun runTimer() {
        val timeView = findViewById<TextView>(R.id.textView)
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val secs = seconds % 60

                val time = String.format(
                    Locale.getDefault(),
                    "%d:%02d:%02d",
                    hours, minutes, secs
                )
                timeView.text = time
                if (running) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        })
    }
}