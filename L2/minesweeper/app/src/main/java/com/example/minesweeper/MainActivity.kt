package com.example.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), TileListener {
    lateinit var grid: RecyclerView
    lateinit var adapter: GridRecyclerAdapter
    lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grid = findViewById(R.id.activity_main_grid)
        grid.layoutManager = GridLayoutManager(this, 9)
        game = Game(9)
        adapter = GridRecyclerAdapter(game.grid.tiles, this)
        grid.adapter = adapter
    }

    override fun onTileClick(tile: Tile){
        Toast.makeText(this, "TileClicked", Toast.LENGTH_SHORT).show()
    }
}