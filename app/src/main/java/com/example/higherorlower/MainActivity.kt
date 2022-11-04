package com.example.higherorlower

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.*
import com.example.higherorlower.model.Card
import com.example.higherorlower.model.CardSuit
import com.example.higherorlower.model.CardValue
import com.example.higherorlower.ui.theme.HigherOrLowerTheme
import com.example.higherorlower.ui.viewmodel.GameState
import com.example.higherorlower.ui.viewmodel.GameViewModel
import com.example.higherorlower.ui.viewmodel.GuessResult
import java.security.AccessController.getContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HigherOrLowerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Container()
                }
            }
        }
    }
}

@Composable
fun HealthBar(lives: Int) {
    Row (horizontalArrangement = Arrangement.Center) {
        Icon(
            if (lives >= 1) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder ,
            contentDescription = "1 Life",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(90.dp)

        )
        Icon(
            if (lives >= 2) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder ,
            contentDescription = "2 Lives",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(90.dp)
        )
        Icon(
            if (lives == 3) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder ,
            contentDescription = "3 Lives",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(90.dp)

        )
    }
}
@Composable
fun HeaderText(lastGuess: GuessResult?){
    Text(
        when (lastGuess) {
            GuessResult.CORRECT -> "Nice one! you were Correct :)"
            GuessResult.INCORRECT -> "Unlucky! you have lost a life :("
            GuessResult.SAME -> "Phew! Same value so you don't lose a life :O"
            else -> "Higher or Lower?" // null
        }
    )
}

@Composable
fun CardImage(card: Card) {
    val imageName = card.getImageName()
    val id: Int = LocalContext.current.resources.getIdentifier(
        imageName,
        "drawable",
        LocalContext.current.packageName
    )
    Image(painter = painterResource(id = id), contentDescription = "", Modifier.padding(50.dp))
}

@Composable
fun Container(viewModel: GameViewModel = viewModel()) {
    val gameState = viewModel.gameState
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        HealthBar(lives = gameState.lives)
        HeaderText(gameState.lastGuess)
        CardImage(card = gameState.previousCard)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HigherOrLowerTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            HealthBar(lives = 2)
            CardImage(card = Card(CardValue.EIGHT, CardSuit.CLUBS))
            Text("hello")
        }
    }
}