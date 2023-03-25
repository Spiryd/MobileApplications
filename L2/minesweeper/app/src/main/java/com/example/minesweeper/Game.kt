package com.example.minesweeper

class Game(size: Int, numOfMines: Int) {
    var grid: GridOfMines
    var clearMode: Boolean = true
    var isOver: Boolean = false
    var timeOut: Boolean = false
    var flagMode: Boolean = false
    var flagCount: Int = 0
    var mineCount: Int

    init {
        grid = GridOfMines(size)
        grid.genGrid(numOfMines)
        mineCount = numOfMines
    }

    fun tileClickHandler(tile: Tile){
        if (!isOver && !isWon() && !timeOut) {
            if (clearMode)
                clear(tile)
            else if (flagMode)
                flag(tile)
        }
    }

    fun clear(tile: Tile){
        var index = grid.tiles.indexOf(tile)
        grid.tiles[index].visited = true

        if (tile.value == 0){
            var toClear: MutableList<Tile> = mutableListOf()
            var toBeChecked: MutableList<Tile> = mutableListOf()

            toBeChecked.add(tile)

            while (toBeChecked.size > 0){
                var tmpTile: Tile = toBeChecked[0]
                var tileIndex: Int = grid.tiles.indexOf(tmpTile)
                var tilePosition = grid.toCoords(tileIndex)
                for (adj in grid.adjTiles(tilePosition.first, tilePosition.second)){
                    if (adj.value == 0){
                        if (!toClear.contains(adj)){
                            if (!toBeChecked.contains(adj)){
                                toBeChecked.add(adj)
                            }
                        }
                    }else{
                        if (!toClear.contains(adj))
                            toClear.add(adj)
                    }
                }
                toBeChecked.remove(tmpTile)
                toClear.add(tmpTile)
            }

            for (tmpTile in toClear){
                tmpTile.visited = true
            }
        } else if (tile.type == TileType.MINE){
            isOver = true
        }
    }

    fun isWon(): Boolean{
        var notVisited = 0
        for (tile in grid.tiles){
            if (tile.type != TileType.MINE && tile.value != 0 && !tile.visited)
                notVisited++
        }
        return notVisited == 0
    }

    fun outOfTime(){
        timeOut = true
    }

    fun toggleMode(){
        clearMode = !clearMode
        flagMode = !flagMode
    }

    fun flag(tile: Tile){
        if(!tile.visited) {
            tile.flagged = !tile.flagged
            var count = 0
            for (tmpTile in grid.tiles) {
                if (tmpTile.flagged)
                    count++
            }
            flagCount = count
        }
    }
}
