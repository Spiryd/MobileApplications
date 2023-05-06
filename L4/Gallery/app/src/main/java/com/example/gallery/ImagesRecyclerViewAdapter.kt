package com.example.gallery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.File

class ImagesRecyclerViewAdapter(private var context: Context, private var images: MutableList<ImageHolder>, private val onClickInterface: OnClickInterface): RecyclerView.Adapter<ImagesRecyclerViewAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val imgFile = File(images[position].path)
        if (imgFile.exists()){
            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(holder.imageView)
        }
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        init{
            imageView = itemView.findViewById(R.id.galleryImage)
            itemView.setOnClickListener {
                val position = adapterPosition
                onClickInterface.onImageClick(position)
            }
        }
    }
}