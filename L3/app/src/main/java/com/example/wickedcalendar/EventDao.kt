package com.example.wickedcalendar

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun allEvents(): Flow<List<Event>>
    @Query("DELETE FROM event WHERE date_String = :date_String")
    suspend fun deleteAllOnDate(date_String: String)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg events: Event)
}