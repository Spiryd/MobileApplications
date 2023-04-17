package com.example.wickedcalendar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity
@Parcelize
class Event(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "date_String") var dateString: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "timeStart_String") var timeStartString: String?,
    @ColumnInfo(name = "timeEnd_String") var timeEndString: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) : Parcelable {
    fun date(): LocalDate{
        return LocalDate.parse(dateString)
    }
    fun timeFrame(): Pair<LocalTime, LocalTime>?{
        return if (timeStartString == null || timeEndString == null){
            null
        } else {
            Pair(LocalTime.parse(timeStartString), LocalTime.parse(timeEndString))
        }
    }

    companion object{
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
    }
}