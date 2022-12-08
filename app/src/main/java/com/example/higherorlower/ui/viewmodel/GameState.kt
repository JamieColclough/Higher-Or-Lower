package com.example.higherorlower.ui.viewmodel

import com.example.higherorlower.model.Card

/**
 * Data class to give the current state of the game
 */
data class UIGameState(
    /**
     * How many lives the user has left.
     */
    var lives: Int = 3,

    /**
     * Amount of times user has guessed correctly.
     */
    var correctGuesses: Int = 0,

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
)

/**
 * Data class to give the current state of the game
 */
data class GameState(
    /**
     * The shuffledDeck to pick cards from.
     */
    var shuffledDeck: MutableList<Card> = mutableListOf(),

    var uiGameState: UIGameState = UIGameState()
)