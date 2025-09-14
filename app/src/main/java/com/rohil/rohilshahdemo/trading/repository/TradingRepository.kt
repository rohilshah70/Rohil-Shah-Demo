package com.rohil.rohilshahdemo.trading.repository

import KtorClient
import com.rohil.network1.vo.ResponseVO
import com.rohil.rohilshahdemo.TradingDataStore
import javax.inject.Inject

class TradingRepository @Inject constructor(
    private val dataStore: TradingDataStore
) {
    private val ktorClient = KtorClient()

    suspend fun getData(
        completionHandler: (ResponseVO?, isFromPref: Boolean?) -> Unit
    ) {
        //Make the api call
        ktorClient.getStock()
            .onSuccess { apiResponse ->
                dataStore.saveStockList(apiResponse)
                completionHandler(apiResponse, false)
            }
            .onFailure { _ ->
                //Check if response is saved in DataStore
                val apiResponse = dataStore.getStockList()

                if (apiResponse != null) {
                    //Response found in DataStore, show a msg that it is local data
                    completionHandler(apiResponse, true)
                } else {
                    //Data not found in DataStore, show error
                    completionHandler(null, null)
                }
            }
    }
}