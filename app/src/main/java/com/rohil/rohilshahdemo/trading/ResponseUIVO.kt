package com.rohil.rohilshahdemo.trading

import androidx.compose.ui.graphics.Color

data class ResponseUIVO(
    val stockList: List<StockUIVO>? = null,
    val bottomBarData: BottomBarUIVO? = null
)

data class StockUIVO(
    val symbol: String? = null,
    val quantity: Int? = null,
    val ltp: Double? = null,
    val profitLoss: Double? = null,
    val profitLossColor: Color? = null
)

data class BottomBarUIVO(
    val totalProfitLoss: Double? = null,
    val currentValue: Double? = null,
    val totalInvestment: Double? = null,
    val todayProfitLoss: Double? = null,
    val profitLossColor: Color? = null,
    val todayProfitLossColor: Color? = null
)