@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tictactoes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tictactoes.ui.theme.TicTacToesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val options = mutableListOf<String>()
            for (i in 3..20){
                options += String.format("$i x $i")
            }
            var expanded by remember { mutableStateOf(false) }
            var selectedText by remember { mutableStateOf(options[0]) }

            TicTacToesTheme {
                Column(){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }
                        ) {
                            TextField(
                                value = selectedText,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                options.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item) },
                                        onClick = {
                                            selectedText = item
                                            expanded = false
                                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                }
                            }
                        }
                        Button(
                            onClick = { newGame() },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text("New Game")
                        }
                    }
                    GameScreen(10)
                }
            }
        }
    }
}

fun newGame(){
    // TODO: make new game
}

@Composable
fun GameScreen(width: Int){
    Box {
        Column() {
            for (i in 1..width){
                Row() {
                    for (j in 1..width){
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            shape = RectangleShape
                        ) {
                            Text(text = "X", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
