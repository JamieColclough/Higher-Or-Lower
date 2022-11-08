package com.example.higherorlower.web

import com.example.higherorlower.model.Card
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://higherorlower-api.netlify.app/"

interface CardApiService {
    @GET("json")
    suspend fun getCards(): MutableList<Card>
}

private val moshi = Moshi.Builder()
    .add(CardAdapter())
    .addLast(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object CardApi {
    val service: CardApiService by lazy{
        retrofit.create(CardApiService::class.java)
    }
}

