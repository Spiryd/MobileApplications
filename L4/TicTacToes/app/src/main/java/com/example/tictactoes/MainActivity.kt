@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tictactoes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tictactoes.ui.theme.TicTacToesTheme

var game = Game(3)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetUp(3, this)
        }
    }
}

@Composable
fun SetUp(width: Int, activity: MainActivity){
    val context = LocalContext.current
    val options = mutableListOf<String>()
    for (i in 3..20){
        options += String.format("$i x $i")
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(options[width - 3]) }

    TicTacToesTheme {
        Column {
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
                    onClick = {
                        val w = options.indexOf(selectedText) + 3
                        game = Game(w)
                        activity.setContent {
                            SetUp(width = w, activity = activity)
                        }
                    },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("New Game")
                }
            }
            GameScreen(width)
        }
    }
}

@Composable
fun GameScreen(width: Int){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val s = (screenWidth / width)
    Column {
        for (i in 0 until width){
            Row {
                for (j in 0 until width){
                    GameSquare(i, j, s)
                }
            }
        }
    }
}

@Composable
fun GameSquare(y: Int, x: Int, s: Int){
    var state by remember { mutableStateOf(" ") }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.DarkGray)
            .size(s.dp)
            .border(width = 1.dp, color = Color.White)
            .clickable {
                val res = game.makeMove(y, x)
                if (res == 1) {
                    state = "X"
                } else if (res == -1) {
                    state = "O"
                }
            }
    ) {
        Text(
            text = state,
            color = Color.White,
        )
    }
}

