package com.rohil.rohilshahdemo.trading.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rohil.rohilshahdemo.R
import com.rohil.rohilshahdemo.trading.BottomBarUIVO

/**
 * @created 13/09/25 - 20:57
 * @project Zoomcar
 * @author Rohil
 * Copyright (c) 2024 Zoomcar. All rights reserved.
 */

@Composable
fun BottomBarView(
    modifier: Modifier = Modifier,
    dataVO: BottomBarUIVO? = null
) {
    val expanded = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .clip(
                RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp
                )
            ),
    ) {
        // Expandable content with animation
        AnimatedVisibility(
            visible = expanded.value,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DetailView(
                    modifier = Modifier,
                    title = stringResource(R.string.current_value) + " ",
                    amount = dataVO?.currentValue,
                    amtColor = Color.Black
                )

                DetailView(
                    modifier = Modifier,
                    title = stringResource(R.string.total_investment) + " ",
                    amount = dataVO?.totalInvestment,
                    amtColor = Color.Black
                )

                DetailView(
                    modifier = Modifier,
                    title = stringResource(R.string.today_profit_loss) + " ",
                    amount = dataVO?.todayProfitLoss,
                    amtColor = dataVO?.todayProfitLossColor
                )

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        DetailView(
            modifier = Modifier,
            title = stringResource(R.string.total_profit_loss) + " ",
            amount = dataVO?.totalProfitLoss,
            amtColor = dataVO?.profitLossColor,
            showExpandIcon = true,
            onExpandClick = {
                expanded.value = expanded.value.not()
            }
        )
    }
}

@Composable
private fun DetailView(
    modifier: Modifier = Modifier,
    title: String? = null,
    amount: Double? = null,
    amtColor: Color? = null,
    showExpandIcon: Boolean? = null,
    onExpandClick: () -> Unit = {}
) {
    amount?.let {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onExpandClick()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                title?.let { title ->
                    Text(
                        modifier = Modifier,
                        text = title,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

                showExpandIcon?.let {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Chevron Down"
                    )
                }
            }

            Text(
                modifier = Modifier,
                text = stringResource(R.string.rs_symbol) + it,
                color = amtColor ?: Color.Black,
                fontSize = 16.sp
            )
        }
    }
}