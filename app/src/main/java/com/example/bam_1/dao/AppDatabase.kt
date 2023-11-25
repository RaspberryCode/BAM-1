package com.example.bam_1.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserNumber::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userNumberDao(): UserNumberDao
}