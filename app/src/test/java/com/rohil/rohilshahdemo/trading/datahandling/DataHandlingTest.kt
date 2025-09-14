package com.rohil.rohilshahdemo.trading.datahandling

import com.google.gson.Gson
import com.rohil.network1.vo.StockDataVO
import com.rohil.rohilshahdemo.trading.usecase.TradingUseCase
import org.junit.Before
import org.junit.Test

class DataHandlingTest {
    private lateinit var usecase: TradingUseCase

    @Before
    fun setup() {
        usecase = TradingUseCase()
    }

    @Test
    fun `valid json should parse correctly`() {
        val json = """
            [
              { "quantity": 10, "ltp": 100.0, "avgPrice": 90.0, "close": 95.0 },
              { "quantity": 5, "ltp": 200.0, "avgPrice": 180.0, "close": 190.0 }
            ]
        """.trimIndent()

        val stocks: Array<StockDataVO> = Gson().fromJson(json, Array<StockDataVO>::class.java)

        assert(stocks.size == 2)
        assert(stocks[0].quantity == 10)
        assert(stocks[0].ltp == 100.0)
    }

    @Test
    fun `empty json array should return empty list`() {
        val json = "[]"
        val stocks: Array<StockDataVO> = Gson().fromJson(json, Array<StockDataVO>::class.java)
        assert(stocks.isEmpty())
        assert(usecase.calculateCurrentValue(stocks.toList()) == 0.0)
    }

    //
    @Test
    fun `missing fields should map to null`() {
        val json = """
            [
              { "quantity": 10, "ltp": 100.0 }
            ]
        """.trimIndent()

        val stocks: Array<StockDataVO> = Gson().fromJson(json, Array<StockDataVO>::class.java)

        assert(stocks[0].avgPrice == null)
        assert(stocks[0].close == null)
        assert(usecase.calculateTotalInvestment(stocks.toList()) == null)
    }

    @Test
    fun `integration of parsing with calculations`() {
        val json = """
            [
              { "quantity": 10, "ltp": 100.0, "avgPrice": 90.0, "close": 95.0 },
              { "quantity": 5, "ltp": 200.0, "avgPrice": 180.0, "close": 190.0 }
            ]
        """.trimIndent()

        val stocks: Array<StockDataVO> = Gson().fromJson(json, Array<StockDataVO>::class.java)

        val currentValue = usecase.calculateCurrentValue(stocks.toList())
        val totalInvestment = usecase.calculateTotalInvestment(stocks.toList())
        val todaysPnL = usecase.calculateTodaysPnL(stocks.toList())

        assert(currentValue == 2000.0)
        assert(totalInvestment == 1800.00)
        assert(todaysPnL == -100.0)
    }
}