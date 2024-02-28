package com.example.counterapp

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast

class CounterService : Service() {
    private val binder = LocalBinder()
    private var counter = 0

    inner class LocalBinder : Binder() {
        fun getService(): CounterService = this@CounterService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun incrementCounter() {
        counter++
    }

    fun decrementCounter() {
        if (counter > 0) {
            counter--
        }
    }

    fun presentValue() {
        Toast.makeText(this, "Current counter value: $counter", Toast.LENGTH_SHORT).show()
    }
}