package com.rohil.rohilshahdemo.trading.usecase

import com.rohil.network1.vo.StockDataVO
import org.junit.Before
import org.junit.Test

class TradingUseCaseTest {

    private lateinit var usecase: TradingUseCase

    @Before
    fun setup() {
        usecase = TradingUseCase()
    }

    /**
     * --------------------------------------------------------------------------
     *
     * Total Profit and loss calculation is correct
     */

    @Test
    fun `both null should return null`() {
        assert(usecase.totalPnL(null, null) == null)
    }

    @Test
    fun `current value null should return null`() {
        assert(usecase.totalPnL(null, 1000.0) == null)
    }

    @Test
    fun `investment null should return null`() {
        assert(usecase.totalPnL(2000.0, null) == null)
    }

    @Test
    fun `profit scenario should return positive difference`() {
        assert(usecase.totalPnL(2000.0, 1500.0) == 500.0)
    }

    @Test
    fun `loss scenario should return negative difference`() {
        assert(usecase.totalPnL(1200.0, 1500.0) == -300.0)
    }

    @Test
    fun `break even should return zero`() {
        assert(usecase.totalPnL(1500.0, 1500.0) == 0.0)
    }

    @Test
    fun `decimal precision should round correctly`() {
        assert(usecase.totalPnL(1000.567, 500.123) == 500.44)
    }

    @Test
    fun `negative values should calculate correctly`() {
        assert(usecase.totalPnL(-100.0, 200.0) == -300.0)
        assert(usecase.totalPnL(200.0, -100.0) == 300.0)

    }

    /**
     * --------------------------------------------------------------------------
     *
     * TEST calculateCurrentValue
     */

    @Test
    fun `single stock should calculate correctly`() {
        val stocks = listOf(StockDataVO(quantity = 10, ltp = 100.0))
        assert(usecase.calculateCurrentValue(stocks) == 1000.0)
    }

    @Test
    fun `multiple stocks should calculate correctly`() {
        val stocks = listOf(
            StockDataVO(quantity = 10, ltp = 100.0),
            StockDataVO(quantity = 5, ltp = 200.0)
        )
        assert(usecase.calculateCurrentValue(stocks) == 2000.0)
    }

    @Test
    fun `empty list should return zero`() {
        val stocks = emptyList<StockDataVO>()
        assert(usecase.calculateCurrentValue(stocks) == 0.0)
    }

    @Test
    fun `null list should return null`() {
        assert(usecase.calculateCurrentValue(null) == null)
    }

    @Test
    fun `stock with null quantity should return null`() {
        val stocks = listOf(StockDataVO(ltp = 100.0))
        assert(usecase.calculateCurrentValue(stocks) == null)
    }

    @Test
    fun `stock with null ltp should return null`() {
        val stocks = listOf(StockDataVO(quantity = 10))
        assert(usecase.calculateCurrentValue(stocks) == null)
    }

    @Test
    fun `stock with zero quantity should not affect total`() {
        val stocks = listOf(
            StockDataVO(quantity = 0, ltp = 200.0),
            StockDataVO(quantity = 5, ltp = 100.0)
        )
        assert(usecase.calculateCurrentValue(stocks) == 500.0)
    }

    @Test
    fun `test calculateCurrentValue is correct`() {
        val result = usecase.calculateCurrentValue(null)
        assert(result == null)
    }

    /**
     * --------------------------------------------------------------------------
     * TEST calculateTotalInvestment
     */

    @Test
    fun `calculateTotalInvestment single stock should calculate correctly`() {
        val stocks = listOf(StockDataVO(quantity = 10, avgPrice = 100.0))
        assert(usecase.calculateTotalInvestment(stocks) == 1000.0)
    }

    @Test
    fun `calculateTotalInvestment multiple stocks should calculate correctly`() {
        val stocks = listOf(
            StockDataVO(quantity = 10, avgPrice = 100.0),
            StockDataVO(quantity = 5, avgPrice = 200.0)
        )
        assert(usecase.calculateTotalInvestment(stocks) == 2000.0)
    }

    @Test
    fun `calculateTotalInvestment empty list should return zero`() {
        val stocks = emptyList<StockDataVO>()
        assert(usecase.calculateTotalInvestment(stocks) == 0.0)
    }

    @Test
    fun `calculateTotalInvestment null list should return null`() {
        assert(usecase.calculateTotalInvestment(null) == null)
    }

    @Test
    fun `calculateTotalInvestment stock with null quantity should return null`() {
        val stocks = listOf(StockDataVO(ltp = 100.0))
        assert(usecase.calculateTotalInvestment(stocks) == null)
    }

    @Test
    fun `stock with null avgPrice should return null`() {
        val stocks = listOf(StockDataVO(quantity = 10))
        assert(usecase.calculateTotalInvestment(stocks) == null)
    }

    @Test
    fun `calculateTotalInvestment stock with zero quantity should not affect total`() {
        val stocks = listOf(
            StockDataVO(quantity = 0, avgPrice = 200.0),
            StockDataVO(quantity = 5, avgPrice = 100.0)
        )
        assert(usecase.calculateTotalInvestment(stocks) == 500.0)
    }

    @Test
    fun `decimal values should round correctly`() {
        val stocks = listOf(StockDataVO(quantity = 2, avgPrice = 99.999))
        assert(usecase.calculateTotalInvestment(stocks) == 200.0)
    }

    @Test
    fun `calculateTotalInvestment negative values should calculate correctly`() {
        val stocks = listOf(StockDataVO(quantity = -10, avgPrice = 100.0))
        assert(usecase.calculateTotalInvestment(stocks) == -1000.0)
    }

    /**
     * TEST calculateTodaysPnL
     */

    @Test
    fun ` calculateTodaysPnLtest calculateTodaysPnL is correct`() {
        val result = usecase.calculateTodaysPnL(null)
        assert(result == null)
    }

    @Test
    fun `single stock profit`() {
        val stocks = listOf(StockDataVO(quantity = 10, close = 120.0, ltp = 100.0))
        assert(usecase.calculateTodaysPnL(stocks) == 200.0)
    }

    @Test
    fun `single stock loss`() {
        val stocks = listOf(StockDataVO(quantity = 10, close = 90.0, ltp = 100.0))
        assert(usecase.calculateTodaysPnL(stocks) == -100.0)
    }

    @Test
    fun `single stock no change`() {
        val stocks = listOf(StockDataVO(quantity = 10, close = 100.0, ltp = 100.0))
        assert(usecase.calculateTodaysPnL(stocks) == 0.0)
    }

    @Test
    fun `multiple stocks mix profit and loss`() {
        val stocks = listOf(
            StockDataVO(quantity = 10, close = 120.0, ltp = 100.0),
            StockDataVO(quantity = 5, close = 90.0, ltp = 100.0)
        )
        assert(usecase.calculateTodaysPnL(stocks) == 150.0)
    }

    @Test
    fun `calculateTodaysPnL empty list should return zero`() {
        val stocks = emptyList<StockDataVO>()
        assert(usecase.calculateTodaysPnL(stocks) == 0.0)
    }

    @Test
    fun `calculateTodaysPnL null list should return null`() {
        assert(usecase.calculateTodaysPnL(null) == null)
    }

    @Test
    fun `calculateTodaysPnL stock with null quantity should return null`() {
        val stocks = listOf(StockDataVO(close = 120.0, ltp = 100.0))
        assert(usecase.calculateTodaysPnL(stocks) == null)
    }

    @Test
    fun `stock with null close should return null`() {
        val stocks = listOf(StockDataVO(quantity = 10, ltp = 100.0))
        assert(usecase.calculateTodaysPnL(stocks) == null)
    }

    @Test
    fun `calculateTodaysPnL stock with null ltp should return null`() {
        val stocks = listOf(StockDataVO(quantity = 10, close = 120.0))
        assert(usecase.calculateTodaysPnL(stocks) == null)
    }

    @Test
    fun `calculateTodaysPnL decimal precision should round correctly`() {
        val stocks = listOf(StockDataVO(quantity = 2, close = 100.567, ltp = 99.111))
        assert(usecase.calculateTodaysPnL(stocks) == 2.91)
    }

    @Test
    fun `calculateTodaysPnL negative values should calculate correctly`() {
        val stocks = listOf(StockDataVO(quantity = -10, close = 120.0, ltp = 100.0))
        assert(usecase.calculateTodaysPnL(stocks) == -200.0)
    }
}