package com.example.minesweeper

class GridOfMines(size: Int){
    var tiles: MutableList<Tile>
    var size: Int

    init {
        this.size = size
        tiles = mutableListOf()
        tiles.fill(Tile(TileType.SAFE))
    }
}