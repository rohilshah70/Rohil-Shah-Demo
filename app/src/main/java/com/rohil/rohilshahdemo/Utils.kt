package com.rohil.rohilshahdemo

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import java.util.Locale

private const val PREFERENCE_STORE = "trading_store"
private const val RESPONSE_KEY = "response"

fun Double.roundTo2Decimal(): Double {
    return String.format(Locale.ROOT,"%.2f", this).toDouble()
}

fun saveResponseInPref(context: Context, responseVO: com.rohil.network1.vo.ResponseVO?) {
    val sharedPreferences =
        context.getSharedPreferences(PREFERENCE_STORE, 0)
    sharedPreferences.edit(commit = true) {
        putString(RESPONSE_KEY, Gson().toJson(responseVO))
    }
}
fun getResponseFromPref(context: Context) : com.rohil.network1.vo.ResponseVO? {
    val sharedPreferences =
        context.getSharedPreferences(PREFERENCE_STORE, 0)
    sharedPreferences.getString(
        RESPONSE_KEY,
        null
    )?.let {
        return Gson().fromJson(it, com.rohil.network1.vo.ResponseVO::class.java)
    }
    return null
}