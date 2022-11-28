package com.example.higherorlower.di

import com.example.higherorlower.web.CardAdapter
import com.example.higherorlower.web.CardApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://higherorlower-api.netlify.app/"

@Module
@InstallIn(ActivityComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesCardApi(): CardApiService {
        val moshi = Moshi.Builder()
            .add(CardAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(CardApiService::class.java)
    }
}