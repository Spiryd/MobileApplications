package com.example.minesweeper

class Tile(type: TileType) {
    var type: TileType
    var flagged: Boolean
    var visited: Boolean

    init {
        this.type = type
        flagged = false
        visited = false
    }

}