package com.example.bam_1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.room.Room
import com.example.bam_1.dao.AppDatabase
import com.example.bam_1.dao.UserNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NumberReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val username = intent.getStringExtra("USER_NAME") ?: "Unknown"
        val number = intent.getIntExtra("NUMBER", -1)
        Log.d("NumberReceiver", "Username: $username, Number: $number")
        val db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        CoroutineScope(Dispatchers.IO).launch {
            val userNumber = UserNumber(username = username, number = number)
            db.userNumberDao().insert(userNumber)
        }
    }
}