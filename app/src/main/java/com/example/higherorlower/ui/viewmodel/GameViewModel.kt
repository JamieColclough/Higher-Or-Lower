package com.example.higherorlower.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.higherorlower.model.Card

/**
 * Data class to give the current state of the game
 */
data class GameState(
    /**
     * The shuffledDeck to pick cards from.
     */
    var shuffledDeck: List<Card> = emptyList(),

    /**
     * How many lives the user has left.
     */
    var lives: Int = 3,

    /**
     * The last card the user picked.
     */
    var PreviousCard: Card? = null,
)

class GameViewModel: ViewModel() {
    var gameState by mutableStateOf(GameState())
        private set

    fun resetGame(){
        gameState.shuffledDeck = gameState.shuffledDeck.shuffled()
        gameState.lives = 3
        gameState.PreviousCard
    }
}

