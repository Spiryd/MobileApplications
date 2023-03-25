package com.example.minesweeper

import kotlin.random.Random

class GridOfMines(size: Int){
    var tiles: MutableList<Tile>
    var size: Int

    init {
        this.size = size
        tiles = mutableListOf()
        for (i in 1..(size * size))
            tiles.add(Tile(TileType.SAFE))
    }

    fun genGrid(numOfMines: Int){
        var minesPlaced = 0

        while (minesPlaced < numOfMines) {
            val index = toIndex(Random.nextInt(size), Random.nextInt(size))
            if (tiles[index].type == TileType.SAFE){
                tiles[index] = Tile(TileType.MINE)
                minesPlaced++
            }
        }

        for (x in 0 until size){
            for (y in 0 until size){
                if (tileAt(x, y)!!.type != TileType.MINE){
                    val adjList: MutableList<Tile> = adjTiles(x, y)
                    var mineCount = 0
                    for (tile in adjList){
                        if (tile.type == TileType.MINE)
                            mineCount++
                    }
                    if (mineCount > 0) {
                        tiles[x + (y * size)] = Tile(TileType.SAFE, mineCount)
                    }
                }
            }
        }
    }

    fun showAllMines(){
        for (tile in tiles){
            if (tile.type == TileType.MINE){
                tile.visited = true
            }
        }
    }

    fun tileAt(x: Int, y: Int): Tile? {
        return if (x < 0 || x>= size || y < 0 || y >= size)
            null
        else
            tiles[toIndex(x, y)]
    }

    fun adjTiles(x: Int, y: Int): MutableList<Tile>{
        val tileList: MutableList<Tile?> = mutableListOf()
        tileList.add(tileAt(x - 1, y))
        tileList.add(tileAt(x + 1, y))
        tileList.add(tileAt(x - 1, y - 1))
        tileList.add(tileAt(x, y - 1))
        tileList.add(tileAt(x + 1, y - 1))
        tileList.add(tileAt(x - 1, y + 1))
        tileList.add(tileAt(x, y + 1))
        tileList.add(tileAt(x + 1, y + 1))

        return  tileList.filterNotNull().toMutableList()
    }

    fun toIndex(x: Int, y: Int): Int{
        return x + (y * size)
    }

    fun toCoords(index: Int): Pair<Int, Int> {
        val y = index / size
        val x = index - ( y * size)
        return Pair(x, y)
    }
}