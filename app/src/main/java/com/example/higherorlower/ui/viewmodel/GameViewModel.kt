package com.example.higherorlower.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.higherorlower.data.CardRepository
import com.example.higherorlower.model.Card
import com.example.higherorlower.web.CardApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the state of the game.
 */
@HiltViewModel
class GameViewModel @Inject constructor(private val cardRepo: CardRepository) : ViewModel() {

    private val _gameState = GameState()
    private val _uiGameState = MutableStateFlow(_gameState.uiGameState)
    val uiState: StateFlow<UIGameState> = _uiGameState.asStateFlow()

    private val _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> = _errorMsg.asStateFlow()

    init {
        resetGame()
    }

    fun resetGame() {
        viewModelScope.launch {
            try {
                val cards = cardRepo.getShuffledCards()
                _gameState.shuffledDeck = cards
                _uiGameState.update {UIGameState()}
                _errorMsg.update { "" }
            } catch (e: Exception) {
                _errorMsg.update { "Error getting cards: ${e.message}" }
            }

        }
    }

    fun pickCard() {
        if (_gameState.shuffledDeck.isNotEmpty()){
            val pickedCard = _gameState.shuffledDeck.removeAt(0)
            _uiGameState.update { state -> state.copy(previousCard = pickedCard) }
        }
    }

    fun makeGuess(guess: Guess) {
        uiState.value.previousCard?.value?.faceValue?.let { previousCardVal ->
            pickCard()
            uiState.value.previousCard?.value?.faceValue?.let { newCardVal ->
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
            --_uiGameState.value.lives
            if (uiState.value.lives == 0) {
                _uiGameState.value.isGameOver = true
            }
        }
        else if (result == GuessResult.CORRECT) {
            _uiGameState.value.correctGuesses ++
        }
        _uiGameState.value.lastGuess = result
    }
}

enum class Guess {
    HIGHER, LOWER
}

enum class GuessResult {
    CORRECT, INCORRECT, SAME
}

