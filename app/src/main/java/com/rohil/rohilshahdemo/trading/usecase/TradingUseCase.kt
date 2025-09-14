package com.rohil.rohilshahdemo.trading.usecase

import androidx.compose.ui.graphics.Color
import com.rohil.rohilshahdemo.roundTo2Decimal
import com.rohil.network1.vo.ResponseVO
import com.rohil.network1.vo.StockDataVO
import com.rohil.rohilshahdemo.trading.BottomBarUIVO
import com.rohil.rohilshahdemo.trading.ResponseUIVO
import com.rohil.rohilshahdemo.trading.StockUIVO
import javax.inject.Inject

class TradingUseCase @Inject constructor() {

    private val profitColor = Color(112, 155, 144)
    private val lossColor = Color.Red

    fun toUiModel(initialResponse: ResponseVO?): ResponseUIVO {
        var data = ResponseUIVO(
            stockList = initialResponse?.data?.userHolding?.map {
                val profitLoss: Double? =
                    if (it.ltp != null && it.avgPrice != null && it.quantity != null) {
                        (it.ltp!! - it.avgPrice!!) * it.quantity!!
                    } else {
                        null
                    }?.roundTo2Decimal()

                StockUIVO(
                    symbol = it.symbol,
                    quantity = it.quantity,
                    ltp = it.ltp,
                    profitLoss = profitLoss,
                    profitLossColor = when {
                        profitLoss == null -> null
                        profitLoss < 0.0 -> lossColor
                        else -> profitColor
                    }
                )
            },
            bottomBarData = BottomBarUIVO(
                currentValue = calculateCurrentValue(initialResponse?.data?.userHolding),
                totalInvestment = calculateTotalInvestment(initialResponse?.data?.userHolding),
                todayProfitLoss = calculateTodaysPnL(initialResponse?.data?.userHolding)
            )
        )
        data = data.copy(
            bottomBarData = data.bottomBarData?.copy(
                totalProfitLoss = totalPnL(
                    data.bottomBarData?.currentValue,
                    data.bottomBarData?.totalInvestment
                )
            )
        )
        data = data.copy(
            bottomBarData = data.bottomBarData?.copy(
                profitLossColor = when {
                    data.bottomBarData?.totalProfitLoss == null -> null
                    (data.bottomBarData?.totalProfitLoss ?: 0.0) < 0.0 -> lossColor
                    else -> profitColor
                },
                todayProfitLossColor = when {
                    data.bottomBarData?.todayProfitLoss == null -> null
                    (data.bottomBarData?.todayProfitLoss ?: 0.0) < 0.0 -> lossColor
                    else -> profitColor
                }
            )
        )

        return data
    }

    fun calculateCurrentValue(stocks: List<StockDataVO>?): Double? {
        return stocks?.map { stock ->
            val qty = stock.quantity ?: return null
            val ltp = stock.ltp ?: return null
            qty * ltp
        }?.sum()
            ?.roundTo2Decimal()
    }

    fun calculateTotalInvestment(stocks: List<StockDataVO>?): Double? {
        return stocks?.map { stock ->
            val qty = stock.quantity ?: return null
            val avgPrice = stock.avgPrice ?: return null
            qty * avgPrice
        }?.sum()
            ?.roundTo2Decimal()
    }

    fun totalPnL(currentValue: Double?, totalInvestment: Double?): Double? {
        if (currentValue == null || totalInvestment == null) return null
        return (currentValue - totalInvestment).roundTo2Decimal()
    }

    fun calculateTodaysPnL(stocks: List<StockDataVO>?): Double? {
        return stocks?.map { stock ->
            val qty = stock.quantity ?: return null
            val close = stock.close ?: return null
            val ltp = stock.ltp ?: return null
            qty * (close - ltp)
        }?.sum()
            ?.roundTo2Decimal()
    }
}