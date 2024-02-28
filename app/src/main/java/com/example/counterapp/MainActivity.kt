package com.example.counterapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.counterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var counterService: CounterService
    private lateinit var binding: ActivityMainBinding
    private var bound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as CounterService.LocalBinder
            counterService = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val views = binding.root
        setContentView(views)


        binding.incrementButton.setOnClickListener {
            if (bound) {
                counterService.incrementCounter()
            }
        }

        binding.decrementButton.setOnClickListener {
            if (bound) {
                counterService.decrementCounter()
            }
        }

        binding.presentButton.setOnClickListener {
            if (bound) {
                counterService.presentValue()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, CounterService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        bound = false
    }
}
