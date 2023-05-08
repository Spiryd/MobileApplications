@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tictactoes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tictactoes.ui.theme.TicTacToesTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameState(
    val size: Int = 3,
    var player: Int = 1,
    val board: Array<IntArray> = Array(size){IntArray(size)}
)

class GameViewModel: ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    fun gameBoard(): Array<IntArray>{
        return _state.value.board
    }

    fun makeMove(y: Int, x: Int){
        _state.update { currentState ->
            currentState.board[y][x] = currentState.player
            currentState.player = -currentState.player
            currentState.copy(
                board = currentState.board,
                player = currentState.player,
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: GameViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect{

                }
            }
        }

        setContent {
            TicTacToesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameBoard(viewModel)
                    Button(onClick = { viewModel.makeMove(1, 1) }) {
                        Text(text = "OK")
                    }
                }

            }
        }


    }
}

@Composable
fun GameBoard(viewModel: GameViewModel) {
    Text("${viewModel}")
}
