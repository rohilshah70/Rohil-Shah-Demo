package com.rohil.rohilshahdemo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.rohil.rohilshahdemo.Constants.DATA_STORE
import com.rohil.rohilshahdemo.IDataStore
import com.rohil.rohilshahdemo.TradingDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext application: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { application.preferencesDataStoreFile(DATA_STORE) }
        )
    }

    @Provides
    @Singleton
    fun provideZoomcarDataStore(dataStore: DataStore<Preferences>): IDataStore {
        return TradingDataStore(dataStore)
    }
}