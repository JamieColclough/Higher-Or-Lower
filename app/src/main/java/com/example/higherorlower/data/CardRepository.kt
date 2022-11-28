package com.example.higherorlower.data

import com.example.higherorlower.model.Card

interface CardRepository {
    suspend fun getShuffledCards(): List<Card>
}