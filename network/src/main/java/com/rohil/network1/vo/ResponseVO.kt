package com.rohil.network1.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @created 12/09/25 - 23:06
 * @project Zoomcar
 * @author Rohil
 * Copyright (c) 2024 Zoomcar. All rights reserved.
 */

@Serializable
data class ResponseVO(
    @SerialName("data")
    val data: UserHoldingVO? = null,
)

@Serializable
data class UserHoldingVO(
    @SerialName("userHolding")
    val userHolding: List<StockDataVO>? = null
)

@Serializable
data class StockDataVO(
    @SerialName("symbol")
    val symbol: String? = null,

    @SerialName("quantity")
    val quantity: Int? = null,

    @SerialName("ltp")
    val ltp: Double? = null,

    @SerialName("avgPrice")
    val avgPrice: Double? = null,

    @SerialName("close")
    val close: Double? = null
)