package com.example.tictactoes

class Game(width: Int) {
    private var board: IntArray

    init {
        board = IntArray(width * width)
    }
}