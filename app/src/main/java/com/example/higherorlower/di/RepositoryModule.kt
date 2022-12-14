package com.example.higherorlower.di

import androidx.lifecycle.ViewModel
import com.example.higherorlower.data.CardRepository
import com.example.higherorlower.data.CardRepositoryImpl
import com.example.higherorlower.web.CardAdapter
import com.example.higherorlower.web.CardApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun providesCardRepository(cardApiService: CardApiService): CardRepository{
        return CardRepositoryImpl(cardApiService)
    }
}