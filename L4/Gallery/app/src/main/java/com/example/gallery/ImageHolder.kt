package com.example.gallery

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
class ImageHolder(var path: String, var uuid: UUID = UUID.randomUUID(), var description: String = "", var rating: Float = 0F) : Parcelable