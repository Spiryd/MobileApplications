package com.example.gradoktorazawadykotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var r1 = 0
    private var r2 = 0
    private var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        score = 0
        roll()
    }

    fun roll() {
        findViewById<TextView>(R.id.points).text = "Punkty: " + score
        r1 = Random.nextInt(16)
        r2 = Random.nextInt(16)
        findViewById<TextView>(R.id.leftButton).text = "" + r1
        findViewById<TextView>(R.id.rightButon).text = "" + r2
    }

    fun rightClick(view: View) {
        if (r1<=r2){
            score++
            Toast.makeText(this, "Dobrze!!", Toast.LENGTH_SHORT).show()
        }
        roll()
    }
    fun leftClick(view: View) {
        if (r1>=r2){
            score++
            Toast.makeText(this, "Dobrze!!", Toast.LENGTH_SHORT).show()
        }
        roll()
    }
}