package com.rohil.rohilshahdemo.trading.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rohil.rohilshahdemo.R
import com.rohil.rohilshahdemo.trading.StockUIVO

/**
 * @created 13/09/25 - 00:32
 * @project Zoomcar
 * @author Rohil
 * Copyright (c) 2024 Zoomcar. All rights reserved.
 */

@Composable
fun StockView(
    modifier: Modifier = Modifier,
    dataVO: StockUIVO
) {

    if (dataVO.ltp != null && dataVO.quantity != null) {

        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                dataVO.symbol?.let {
                    Text(
                        modifier = Modifier,
                        text = it,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }

                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(color = Color.Gray, fontSize = 12.sp)) {
                            append(stringResource(R.string.ltp) + " ")
                        }
                        withStyle(
                            SpanStyle(
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append(stringResource(R.string.rs_symbol) + dataVO.ltp)
                        }
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(color = Color.Gray, fontSize = 12.sp)) {
                            append(stringResource(R.string.net_qty) + " ")
                        }
                        withStyle(
                            SpanStyle(
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append(dataVO.quantity.toString())
                        }
                    }
                )

                dataVO.profitLoss?.let {
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.Gray, fontSize = 12.sp)) {
                                append(stringResource(R.string.pl) + " ")
                            }
                            withStyle(
                                SpanStyle(
                                    color = dataVO.profitLossColor ?: Color.Black,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(stringResource(R.string.rs_symbol) + it)
                            }
                        }
                    )
                }
            }
        }
    }
}