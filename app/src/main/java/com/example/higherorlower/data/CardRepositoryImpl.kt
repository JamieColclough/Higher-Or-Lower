package com.example.higherorlower.data

import com.example.higherorlower.model.Card
import com.example.higherorlower.web.CardApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CardRepositoryImpl(private val cardApi: CardApiService): CardRepository {
    override suspend fun getShuffledCards(): MutableList<Card> {
        var cards = cardApi.getCards();
        cards.shuffle();
        return cards;
    }
}