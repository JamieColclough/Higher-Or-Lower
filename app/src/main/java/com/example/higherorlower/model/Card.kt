package com.example.higherorlower.model

data class Card(val value: CardValue, val suit: CardSuit) {
    fun getImageName(): String {
        return  "${value.name.lowercase()}_of_${suit.stringVal}"
    }
}

enum class CardValue(val stringVal: String) {
    ACE("A"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("jack"),
    QUEEN("queen"),
    KING("king"),
}

enum class CardSuit(val stringVal: String) {
    SPADES("spades"),
    HEARTS("hearts"),
    CLUBS("clubs"),
    DIAMONDS("diamonds")
}
