package com.example.higherorlower.model

data class Card(val value: CardValue, val suit: CardSuit) {
    fun getImageName(): String {
        return  "${value.name.lowercase()}_of_${suit.stringVal}"
    }
}

enum class CardValue(val stringVal: String, val faceValue: Int) {
    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("jack", 11),
    QUEEN("queen", 12),
    KING("king", 13);

    companion object {
        fun find(value: String): CardValue? = CardValue.values().find { it.stringVal == value }
    }
}

enum class CardSuit(val stringVal: String) {
    SPADES("spades"),
    HEARTS("hearts"),
    CLUBS("clubs"),
    DIAMONDS("diamonds");

    companion object {
        fun find(value: String): CardValue? = CardValue.values().find { it.stringVal == value }
    }
}
