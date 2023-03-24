package com.example.minesweeper

class Game(size: Int) {
    var grid: GridOfMines

    init {
        grid = GridOfMines(size)
    }
}