package com.example.tictactoes

class Game(private var width: Int) {
    private var board = Array(width){IntArray(width)}
    private var turn = 1

    fun checkWin(){
        for (i in 0 until width){

        }
    }

    fun makeMove(y: Int, x: Int): Int{
        if ( board[y][x] == 0 ){
            board[y][x] = turn
            turn = -turn
            return -turn
        }
        return 0
    }
}