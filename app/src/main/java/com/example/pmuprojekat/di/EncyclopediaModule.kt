package com.example.pmuprojekat.di

import com.example.pmuprojekat.data.encyclopedia.EncyclopediaRepository
import com.example.pmuprojekat.data.encyclopedia.LocalEncyclopediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EncyclopediaModule {
    @Binds
    @Singleton
    abstract fun bindEncyclopediaRepository(
        repository: LocalEncyclopediaRepository
    ): EncyclopediaRepository
}
