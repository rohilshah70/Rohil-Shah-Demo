package com.rohil.rohilshahdemo.trading

import androidx.compose.ui.graphics.Color

/**
 * @created 13/09/25 - 00:03
 * @project Zoomcar
 * @author Rohil
 * Copyright (c) 2024 Zoomcar. All rights reserved.
 */

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