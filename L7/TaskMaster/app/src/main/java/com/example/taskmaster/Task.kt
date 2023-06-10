package com.example.taskmaster

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import taskdb.TaskEntity

@Parcelize
class Task( var id: Long = 0, var description: String): Parcelable {

}