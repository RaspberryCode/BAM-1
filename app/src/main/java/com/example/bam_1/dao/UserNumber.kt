package com.example.bam_1.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserNumber(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val number: Int
)