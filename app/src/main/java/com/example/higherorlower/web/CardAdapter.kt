package com.example.higherorlower.web

import com.example.higherorlower.model.Card
import com.example.higherorlower.model.CardSuit
import com.example.higherorlower.model.CardValue
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonDataException

class CardAdapter {
    @FromJson
    fun fromJson(cardJson: CardJson): Card {
        val cardValue = CardValue.find(cardJson.value)
        val cardSuit = CardSuit.find(cardJson.suit)
        if (cardValue == null || cardSuit == null) throw JsonDataException("Unknown card: $cardJson")
        return Card(cardValue, cardSuit)
    }

    @JsonClass(generateAdapter = true)
    data class CardJson (
        val value: String,
        val suit: String
    )
}