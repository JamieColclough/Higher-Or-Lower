package com.example.higherorlower.data

import com.example.higherorlower.model.Card
import com.example.higherorlower.web.CardApiService

class CardRepositoryImpl(private val cardApi: CardApiService): CardRepository {
    override suspend fun getShuffledCards(): List<Card>{
        return cardApi.getCards().shuffled()
    }
}