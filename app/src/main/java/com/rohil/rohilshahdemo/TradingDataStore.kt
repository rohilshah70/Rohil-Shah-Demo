package com.rohil.rohilshahdemo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rohil.network1.vo.ResponseVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TradingDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) :
    IDataStore {
    private val deserializeJSON = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private fun getStringPreferenceKey(key: String): Preferences.Key<String> {
        return stringPreferencesKey(key)
    }

    override suspend fun putString(prefName: String?, value: String?) {
        dataStore.edit { preferences ->
            preferences[getStringPreferenceKey(prefName ?: "")] = value ?: ""
        }
    }

    override fun getString(prefName: String): Flow<String?> {
        return dataStore.data.map {
            it[getStringPreferenceKey(prefName)]
        }
    }

    override suspend fun saveStockList(responseVO: ResponseVO?) {
        val cityDetailsStr = deserializeJSON.encodeToString(responseVO)
        putString(Constants.RESPONSE_KEY, cityDetailsStr)
    }

    override suspend fun getStockList(): ResponseVO? {
        val responseStr = getString(
            Constants.RESPONSE_KEY
        ).firstOrNull()
        return if (responseStr.isNullOrEmpty()) null else
            Json.decodeFromString<ResponseVO>(responseStr)
    }
}
