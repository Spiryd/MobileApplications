package com.example.minesweeper

class Tile(type: TileType, value: Int = 0) {
    var type: TileType
    var flagged: Boolean
    var visited: Boolean
    var value: Int

    init {
        this.type = type
        if (type == TileType.MINE)
            this.value = -1
        else
            this.value = value
        flagged = false
        visited = false
    }

}