package com.rohil.rohilshahdemo

import com.rohil.network1.vo.ResponseVO
import kotlinx.coroutines.flow.Flow

interface IDataStore {
    suspend fun putString(prefName: String?, value: String?)
    fun getString(prefName: String): Flow<String?>
    suspend fun saveStockList(responseVO: ResponseVO?)
    suspend fun getStockList(): ResponseVO?
}