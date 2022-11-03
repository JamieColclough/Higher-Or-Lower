package com.example.higherorlower

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.*
import com.example.higherorlower.model.Card
import com.example.higherorlower.ui.theme.HigherOrLowerTheme
import com.example.higherorlower.ui.viewmodel.GameState
import com.example.higherorlower.ui.viewmodel.GameViewModel
import java.security.AccessController.getContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HigherOrLowerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android")
                    Container()
                }
            }
        }
    }
}

@Composable
fun CardImage(state: GameState) {
    state.PreviousCard?.let {
        val imageName = it.getImageName()
        val id: Int = LocalContext.current.resources.getIdentifier(imageName, "drawable", LocalContext.current.packageName)
        Image(painter = painterResource(id = id),contentDescription = "")
    }
}

@Composable
fun Container(viewModel: GameViewModel = viewModel()) {
    viewModel.resetGame()
    CardImage(state= viewModel.gameState)
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HigherOrLowerTheme {
        Greeting("Android")
    }
}