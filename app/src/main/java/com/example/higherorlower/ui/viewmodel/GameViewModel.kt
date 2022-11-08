package com.example.higherorlower.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.higherorlower.model.Card
import com.example.higherorlower.web.CardApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
    var previousCard: Card? = null,

    /**
     * Whether the last guess the user made was correct or not.
     */
    var lastGuess: GuessResult? = null,

    /**
     * Whether the game has ended.
     */
    var isGameOver: Boolean = false
) {
}

/**
 * ViewModel for the state of the game.
 */
class GameViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> = _errorMsg.asStateFlow()

    init {
        resetGame()
    }

    fun resetGame() {
        viewModelScope.launch {
            try {
                val cards = CardApi.service.getCards()
                cards.shuffle()
                _gameState.update {
                    GameState(cards, 3, null, null, false)
                }
                _errorMsg.update { "" }
            } catch (e: Exception) {
                _errorMsg.update { "Error getting cards: ${e.message}" }
            }

        }
    }

    fun pickCard() {
        val deck = _gameState.value.shuffledDeck
        if (deck.count() > 0){
            val pickedCard = deck.removeAt(0)
            _gameState.update { state -> state.copy(previousCard = pickedCard) }
        }
    }

    fun makeGuess(guess: Guess) {
        gameState.value.previousCard?.value?.faceValue?.let { previousCardVal ->
            pickCard()
            gameState.value.previousCard?.value?.faceValue?.let { newCardVal ->
                when {
                    newCardVal == previousCardVal -> {
                        guessCallBack(GuessResult.SAME)
                    } // If cards equal, gets away with it either way
                    newCardVal > previousCardVal -> { // Higher
                        guessCallBack(if (guess == Guess.HIGHER) GuessResult.CORRECT else GuessResult.INCORRECT)
                    }
                    else -> { // Lower
                        guessCallBack(if (guess == Guess.LOWER) GuessResult.CORRECT else GuessResult.INCORRECT)
                    }
                }
            }
        }
    }

    private fun guessCallBack(result: GuessResult) {
        if (result == GuessResult.INCORRECT) {
            --gameState.value.lives
            if (gameState.value.lives == 0) {
                _gameState.value.isGameOver = true
            }
        }
        _gameState.value.lastGuess = result
    }
}

enum class Guess {
    HIGHER, LOWER
}

enum class GuessResult {
    CORRECT, INCORRECT, SAME
}

