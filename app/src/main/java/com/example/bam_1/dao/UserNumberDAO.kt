package com.example.bam_1.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserNumberDao {
    @Insert
    suspend fun insert(userNumber: UserNumber)

    @Query("SELECT * FROM UserNumber")
    suspend fun getAll(): List<UserNumber>
}