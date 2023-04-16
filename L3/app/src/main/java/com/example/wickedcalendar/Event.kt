package com.example.wickedcalendar

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.*

@Parcelize
class Event(var title: String, var date: LocalDate, var description: String, var id: UUID = UUID.randomUUID()) : Parcelable {

}