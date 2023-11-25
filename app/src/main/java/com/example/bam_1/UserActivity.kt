package com.example.bam_1

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.bam_1.dao.AppDatabase
import com.example.bam_1.ui.theme.BAM1Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserActivity : ComponentActivity() {
    private lateinit var numberReceiver: NumberReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userName = intent.getStringExtra("USER_NAME") ?: "No name provided"
        // Replace the UI with Composable function
        setContent {
            BAM1Theme {
                UserActivityComponent(userName)
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun UserActivityPreview() {
        BAM1Theme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                UserActivityComponent("Preview")
            }
        }
    }

    @Composable
    private fun UserActivityComponent(userName: String) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Text(
                    text = "Hello, $userName!", modifier = Modifier.padding(24.dp)
                )
                Button(onClick = {
                    val startIntent = Intent(this@UserActivity, TimerService::class.java)
                    startIntent.putExtra("USER_NAME", userName)
                    startService(startIntent)
                }) {
                    Text("Start Service")
                }
                Button(onClick = {
                    val stopIntent = Intent(this@UserActivity, TimerService::class.java)
                    stopService(stopIntent)
                }) {
                    Text("Stop Service")
                }
                Button(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val db = Room.databaseBuilder(
                            applicationContext,
                            AppDatabase::class.java, "database-name"
                        ).build()

                        val userNumbers = db.userNumberDao().getAll()
                        userNumbers.forEach { userNumber ->
                            Log.d(
                                "UserActivity",
                                "Row: ${userNumber.id}, Username: ${userNumber.username}, Number: ${userNumber.number}"
                            )
                        }
                    }
                }) {
                    Text("Log Database Entries")
                }
            }
        }
        numberReceiver = NumberReceiver()
        val intentFilter = IntentFilter("com.example.ACTION_SEND_NUMBER")
        registerReceiver(numberReceiver, intentFilter)
    }

    override fun onDestroy() {
        // Unregister NumberReceiver
        unregisterReceiver(numberReceiver)
        super.onDestroy()
    }
}


