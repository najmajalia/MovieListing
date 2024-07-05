package com.id.di

import android.content.Context
import com.id.data.user.source.UserDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStoreUser(@ApplicationContext context: Context): UserDataStore {
        return UserDataStore(context)
    }
}