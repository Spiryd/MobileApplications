package com.example.nienajlepszaaplikacja

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var numberToBeGuessed: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberToBeGuessed = Random.nextInt(0, 1000)
    }

    fun click(view: View) {
        var guess =  findViewById<EditText>(R.id.editTextNumber)
        var text = findViewById<TextView>(R.id.textView)
        if (Integer.parseInt(guess.text.toString()) > numberToBeGuessed){
            text.text = "Za Dużo!!!"
            text.setTextColor(Color.parseColor("#e81717"))
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40F)
        }else if(Integer.parseInt(guess.text.toString()) < numberToBeGuessed){
            text.text = "Za Mało!!!"
            text.setTextColor(Color.parseColor("#a917e8"))
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40F)
        }else {
            text.text = "Dobrze!!!"
            text.setTextColor(Color.parseColor("#17e848"))
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80F)
            numberToBeGuessed = Random.nextInt(0, 1000)
        }
    }

}