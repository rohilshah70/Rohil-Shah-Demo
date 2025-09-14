package com.rohil.rohilshahdemo.trading.repository

import android.content.Context
import KtorClient
import com.rohil.network1.vo.ResponseVO
import com.rohil.rohilshahdemo.getResponseFromPref
import com.rohil.rohilshahdemo.saveResponseInPref
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @created 12/09/25 - 23:26
 * @project Zoomcar
 * @author Rohil
 * Copyright (c) 2024 Zoomcar. All rights reserved.
 */

class TradingRepository @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val ktorClient = KtorClient()

    suspend fun getData(
        completionHandler: (ResponseVO?, isFromPref: Boolean?) -> Unit
    ) {
        //Make the api call
        ktorClient.getStock()
            .onSuccess { apiResponse ->
                saveResponseInPref(context = context, responseVO = apiResponse)
                println("Saved data --> ${getResponseFromPref(context)}")
                completionHandler(apiResponse, false)
            }
            .onFailure { _ ->
                //Check if response is saved in SharedPreference
                val apiResponse = getResponseFromPref(context)

                if (apiResponse != null) {
                    //Response found in shared pref, show a msg that it is local data
                    completionHandler(apiResponse, true)
                } else {
                    //Data not found in shared pref, show error
                    completionHandler(null, null)
                }
            }
    }
}