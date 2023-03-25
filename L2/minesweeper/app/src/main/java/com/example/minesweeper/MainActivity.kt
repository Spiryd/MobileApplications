package com.example.minesweeper

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), TileListener {
    lateinit var grid: RecyclerView
    lateinit var adapter: GridRecyclerAdapter
    lateinit var game: Game
    lateinit var smiley: TextView
    lateinit var timer: TextView
    lateinit var flag: TextView
    lateinit var flagCount: TextView
    lateinit var countDownTimer: CountDownTimer
    var timeElapsed = 0
    var timerStarted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flag = findViewById(R.id.activity_main_flag)
        flagCount = findViewById(R.id.activity_main_flagsleft)
        flag.setOnClickListener {
            game.toggleMode()
            if (game.flagMode){
                var border: GradientDrawable = GradientDrawable()
                border.setColor(Color.WHITE)
                border.setStroke(4, Color.GRAY)
                flag.background = border
            }else{
                var border: GradientDrawable = GradientDrawable()
                border.setColor(Color.WHITE)
                flag.background = border
            }
        }

        smiley = findViewById(R.id.activity_main_smiley)
        smiley.setOnClickListener {
            game = Game(9, 9)
            adapter.tiles = game.grid.tiles
            timerStarted = false
            countDownTimer.cancel()
            timeElapsed = 0
            timer.setText(R.string.default_count)
            flagCount.text = String.format("%03d", game.mineCount - game.flagCount)
        }

        timer = findViewById(R.id.activity_main_timer)
        countDownTimer = object: CountDownTimer(999000, 1000) {
            override fun onTick(p0: Long) {
                timeElapsed++
                timer.text = String.format("%03d", timeElapsed)
            }

            override fun onFinish() {
                game.outOfTime()
                Toast.makeText(parent, "Game Over: Timer Expired", Toast.LENGTH_SHORT).show()
                game.grid.showAllMines()
                adapter.tiles = game.grid.tiles
            }
        }

        grid = findViewById(R.id.activity_main_grid)
        grid.layoutManager = GridLayoutManager(this, 9)
        game = Game(9, 9)
        adapter = GridRecyclerAdapter(game.grid.tiles, this)
        grid.adapter = adapter
        flagCount.text = String.format("%03d", game.mineCount - game.flagCount)
    }

    override fun onTileClick(tile: Tile){
        Log.i("onTileClick", "onTileClick")
        game.tileClickHandler(tile)

        flagCount.text = String.format("%03d", game.mineCount - game.flagCount)

        if (!timerStarted){
            countDownTimer.start()
            timerStarted = true
        }

        if (game.isOver) {
            Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show()
            game.grid.showAllMines()
        }

        if (game.isWon()){
            Toast.makeText(this, "You Win", Toast.LENGTH_SHORT).show()
            game.grid.showAllMines()
        }

        adapter.tiles = game.grid.tiles
    }
}