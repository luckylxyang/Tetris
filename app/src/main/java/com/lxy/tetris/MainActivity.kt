package com.lxy.tetris

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lxy.tetris.ui.theme.TetrisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        setContent {
            TetrisTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {

                    GameScreenApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenApp() {
    val viewModel: TetrisViewModel = viewModel()

    if (viewModel.uiState.value) {
        GameScreen(viewModel = viewModel)
    } else {
        // 显示开始游戏按钮，并在点击时调用 startGame 方法
        Button(
            onClick = { viewModel.startGame() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Start Game")
        }
    }
}