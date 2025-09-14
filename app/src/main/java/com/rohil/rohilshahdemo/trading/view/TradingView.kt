package com.rohil.rohilshahdemo.trading.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rohil.rohilshahdemo.CustomShadowType
import com.rohil.rohilshahdemo.trading.viewmodel.TradingEvent
import com.rohil.rohilshahdemo.trading.viewmodel.TradingViewModel
import com.rohil.rohilshahdemo.R
import com.rohil.rohilshahdemo.customShadow

/**
 * @created 12/09/25 - 23:35
 * @project Zoomcar
 * @author Rohil
 * Copyright (c) 2024 Zoomcar. All rights reserved.
 */

@Composable
fun TradingView(
    viewModel: TradingViewModel
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle().value

    BackHandler(
        enabled = true,
        onBack = {
            viewModel.setEvent(TradingEvent.BackPressed)
        }
    )

    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        containerColor = Color.White,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 40.dp)
                    .background(colorResource(id = R.color.purple_500))
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 12.dp,
                            top = 8.dp
                        ),
                    text = stringResource(id = R.string.toolbar_title) + if(uiState.isOffline)
                        " - ${stringResource(id = R.string.offline_text)}" else "",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        },
        bottomBar = {
            BottomBarView(
                modifier = Modifier
                    .customShadow(
                        type = CustomShadowType.HIGH_INVERSE,
                        roundRadius = 12.dp
                    ),
                dataVO = uiState.response?.bottomBarData
            )
        }
    ) { padding ->

        uiState.response?.stockList?.let {
            LazyColumn(
                modifier = Modifier
                    .padding(padding),
                contentPadding = PaddingValues(
                    vertical = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(it){ index, item ->
                    StockView(
                        modifier = Modifier
                            .padding(horizontal = 12.dp),
                        dataVO = item
                    )

                    if (index != it.lastIndex){
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }


    if (uiState.showLoader) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.75f))
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = colorResource(id = R.color.purple_500)
            )
        }
    }

    if (uiState.showError){
        ErrorView()
    }
}