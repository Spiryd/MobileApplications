package com.example.hangman

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var words: Array<String>
    lateinit var game: Game
    lateinit var wordView: TextView
    var mutedButtons: MutableList<Button> = mutableListOf()
    lateinit var hangmanImg: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wordView = findViewById(R.id.wordView)
        hangmanImg = findViewById(R.id.wisielecImg)

        words = resources.getStringArray(R.array.words)
        game = Game(words[Random.nextInt(words.size)])
        wordView.text = game.toText()
    }

    fun unmuteButtons(){
        for (button in mutedButtons){
            button.isClickable = true
            button.setBackgroundColor(Color.parseColor("#FF6200EE"))
        }
    }

    fun click(view: View){
        val button: Button = findViewById(view.id)
        if(!game.handleOnClick(button.text)){
            when(game.numberOfFailedGuesses){
                1 -> hangmanImg.setBackgroundResource(R.drawable.wisielec1)
                2 -> hangmanImg.setBackgroundResource(R.drawable.wisielec2)
                3 -> hangmanImg.setBackgroundResource(R.drawable.wisielec3)
                4 -> hangmanImg.setBackgroundResource(R.drawable.wisielec4)
                5 -> hangmanImg.setBackgroundResource(R.drawable.wisielec5)
                6 -> hangmanImg.setBackgroundResource(R.drawable.wisielec6)
                7 -> hangmanImg.setBackgroundResource(R.drawable.wisielec7)
                8 -> hangmanImg.setBackgroundResource(R.drawable.wisielec8)
                9 -> hangmanImg.setBackgroundResource(R.drawable.wisielec9)

            }
        }
        mutedButtons.add(button)
        button.isClickable = false
        button.setBackgroundColor(Color.GRAY)
        if (game.gameWon){
            Toast.makeText(this, "You Win", Toast.LENGTH_SHORT).show()
            unmuteButtons()
            game = Game(words[Random.nextInt(words.size)])
            hangmanImg.setBackgroundResource(R.drawable.wisielec0)
        }
        if (game.gameOver){
            Toast.makeText(this, "You loose", Toast.LENGTH_SHORT).show()
            unmuteButtons()
            game = Game(words[Random.nextInt(words.size)])
            hangmanImg.setBackgroundResource(R.drawable.wisielec0)
        }
        wordView.text = game.toText()
    }
}