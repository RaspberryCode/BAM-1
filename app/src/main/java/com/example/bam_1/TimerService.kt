package com.example.bam_1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class TimerService : Service() {
    private val serviceScope = CoroutineScope(Dispatchers.Default)
    private var counter = 0
    private lateinit var job: Job
    private var username: String? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        username = intent?.getStringExtra("USER_NAME") ?: "Unknown"

        // Send the username at service start
        sendBroadcastMessage(username ?: "Unknown", counter)

        job = serviceScope.launch {
            while (isActive) {
                Log.d("TimerService", "Service $startId counter: $counter")
                counter++
                delay(1000)
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        job.cancel()
        serviceScope.cancel()

        // Send the username and number when service is stopped
        sendBroadcastMessage(username ?: "Unknown", counter)

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun sendBroadcastMessage(username: String, number: Int) {
        Intent("com.example.ACTION_SEND_NUMBER").also { intent ->
            intent.putExtra("USER_NAME", username)
            intent.putExtra("NUMBER", number)
            sendBroadcast(intent)
        }
    }
}