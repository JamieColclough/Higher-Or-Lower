package com.example.higherorlower.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.higherorlower.model.Card
import com.example.higherorlower.model.CardSuit
import com.example.higherorlower.model.CardValue

/**
 * Data class to give the current state of the game
 */
data class GameState(
    /**
     * The shuffledDeck to pick cards from.
     */
    var shuffledDeck: MutableList<Card> = mutableListOf(),

    /**
     * How many lives the user has left.
     */
    var lives: Int = 3,

    /**
     * The last card the user picked.
     */
    var PreviousCard: Card? = null,
)

/**
 * ViewModel for the state of the game.
 */
class GameViewModel : ViewModel() {
    var gameState by mutableStateOf(GameState())
        private set



    fun resetGame() {
        gameState.shuffledDeck = getCards()
        gameState.shuffledDeck.shuffle()
        gameState.lives = 3
        gameState.PreviousCard = gameState.shuffledDeck.removeAt(0)
    }

    /**
     * Func to get card list (will be updated to include api call)
     */
    fun getCards(): MutableList<Card> {
        val cards: MutableList<Card> = mutableListOf()
        CardSuit.values().forEach { suit ->
            CardValue.values().forEach { value ->
                cards.add(Card(value, suit))
            }
        }
        return cards
    }
}

