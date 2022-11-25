package com.example.higherorlower

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.higherorlower.model.Card
import com.example.higherorlower.ui.theme.HigherOrLowerTheme
import com.example.higherorlower.ui.viewmodel.GameViewModel
import com.example.higherorlower.ui.viewmodel.Guess
import com.example.higherorlower.ui.viewmodel.GuessResult

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
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun HealthBar(lives: Int) {
    Row(horizontalArrangement = Arrangement.Center) {
        Icon(
            if (lives >= 1) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "1 Life",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(90.dp)

        )
        Icon(
            if (lives >= 2) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "2 Lives",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(90.dp)
        )
        Icon(
            if (lives == 3) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "3 Lives",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(90.dp)

        )
    }
}

@Composable
fun HeaderText(lastGuess: GuessResult?, isGameOver: Boolean) {
    val headerText: Pair<String, String> = when (lastGuess) {
        GuessResult.CORRECT -> Pair("Nice one!", "you were Correct :)")
        GuessResult.INCORRECT -> if (!isGameOver) Pair("Unlucky!", "you have lost a life :(") else Pair("GAME OVER!","")
        GuessResult.SAME -> Pair("Phew!", "Same value so you don't lose a life :O")
        else -> Pair("Welcome!", "Click below to start... ") // null
    }
    Text(headerText.first, fontWeight = FontWeight.Bold, fontSize = 30.sp)
    Text(headerText.second, fontWeight = FontWeight.Normal, fontSize = 15.sp)
}

@Composable
fun CardImage(card: Card?) {
    val imageName = card?.getImageName() ?: "back"
    val id: Int = LocalContext.current.resources.getIdentifier(
        imageName,
        "drawable",
        LocalContext.current.packageName
    )
    Image(painter = painterResource(id = id), contentDescription = "",
        Modifier
            .padding(50.dp)
            .fillMaxWidth())
}

@Composable
fun CorrectGuessText(correctGuesses: Int) {
    val guessString = "You have made ${correctGuesses} correct guesses."
    Text(guessString, fontWeight = FontWeight.Bold, fontSize = 15.sp)
}

@Composable
fun ButtonLabelText(isGameOver: Boolean, anyCardBeenTurnedOver: Boolean) {
    Text(if (isGameOver) "Play again?" else if (anyCardBeenTurnedOver) "Higher or Lower?" else "Turn over to start")
}

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val gameState by viewModel.gameState.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .padding(vertical = 30.dp)
    ) {
        HealthBar(lives = gameState.lives)
        HeaderText(gameState.lastGuess, gameState.isGameOver)
        Text(errorMsg, color = MaterialTheme.colors.error)
        CardImage(card = gameState.previousCard)
        CorrectGuessText(correctGuesses = gameState.correctGuesses)
        Spacer(modifier = Modifier.weight(1f))
        ButtonLabelText(isGameOver = gameState.isGameOver, anyCardBeenTurnedOver = gameState.previousCard != null)
        Row( horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
            if (gameState.isGameOver){
                Button(onClick = {viewModel.resetGame()}){Text("New Game")}
            }
            else if (gameState.previousCard == null){
                Button(onClick = {viewModel.pickCard()}){Text("Pick first card")}
            }
            else{
                Button(onClick = {viewModel.makeGuess(Guess.LOWER)}){Text("Lower")}
                Spacer(modifier = Modifier.width(20.dp))
                Button(onClick = {viewModel.makeGuess(Guess.HIGHER)}){Text("Higher")}
            }
        }
    }
}