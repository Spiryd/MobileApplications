package com.example.minesweeper

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GridRecyclerAdapter(tiles: MutableList<Tile>, listener: TileListener):
    RecyclerView.Adapter<GridRecyclerAdapter.MineTileViewHolder>() {
    private var tiles: MutableList<Tile> = mutableListOf()
        set(tiles) {
            field = tiles
            notifyDataSetChanged()
        }

    var listener: TileListener

    init {
        this.tiles = tiles
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MineTileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return MineTileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MineTileViewHolder, position: Int) {
        holder.bind(tiles[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return tiles.size
    }

    inner class MineTileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var valueTextView: TextView

        init{
            valueTextView = itemView.findViewById(R.id.item_cell_value)
        }

        fun bind(tile: Tile){
            itemView.setBackgroundColor(Color.GRAY)

            itemView.setOnClickListener { listener.onTileClick(tile) }
        }

    }
}