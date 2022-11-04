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

    var lastGuess: GuessResult? = null,

    var isGameOver: Boolean = false
) {
    /**
     * The last card the user picked.
     */
    lateinit var previousCard: Card;
}

/**
 * ViewModel for the state of the game.
 */
class GameViewModel : ViewModel() {
    var gameState by mutableStateOf(GameState())
        private set

    init {
        resetGame()
    }

    fun resetGame() {
        gameState.shuffledDeck = getCards()
        gameState.shuffledDeck.shuffle()
        gameState.lives = 3
        gameState.previousCard = gameState.shuffledDeck.removeAt(0)
    }

    fun makeGuess(guess: Guess) {
        val pickedCard = gameState.shuffledDeck.removeAt(0)
        val previousCardVal = gameState.previousCard.value.faceValue

        pickedCard.value.faceValue.let {
            when {
                it == previousCardVal -> {guessCallBack(GuessResult.SAME)} // If cards equal, gets away with it either way
                it > previousCardVal -> { // Higher
                    guessCallBack(if(guess == Guess.HIGHER) GuessResult.CORRECT else GuessResult.INCORRECT)
                }
                else -> { // Lower
                    guessCallBack(if(guess == Guess.LOWER) GuessResult.CORRECT else GuessResult.INCORRECT)
                }
            }
        }
    }

    private fun guessCallBack(result: GuessResult) {
        if (result == GuessResult.INCORRECT) {
            --gameState.lives
            if (gameState.lives == 0) {
                gameState.isGameOver = true
            }
        }
        gameState.lastGuess = result;
    }

    /**
     * Func to get card list (will be updated to include api call)
     */
    private fun getCards(): MutableList<Card> {
        val cards: MutableList<Card> = mutableListOf()
        CardSuit.values().forEach { suit ->
            CardValue.values().forEach { value ->
                cards.add(Card(value, suit))
            }
        }
        return cards
    }
}

enum class Guess {
    HIGHER, LOWER
}

enum class GuessResult {
    CORRECT, INCORRECT, SAME
}

