package com.rohil.rohilshahdemo.trading.viewmodel

import androidx.lifecycle.viewModelScope
import com.rohil.rohilshahdemo.mvi.MviEffect
import com.rohil.rohilshahdemo.mvi.MviEvent
import com.rohil.rohilshahdemo.mvi.MviState
import com.rohil.rohilshahdemo.mvi.MviViewModel
import com.rohil.rohilshahdemo.trading.ResponseUIVO
import com.rohil.rohilshahdemo.trading.repository.TradingRepository
import com.rohil.rohilshahdemo.trading.usecase.TradingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @created 12/09/25 - 23:19
 * @project Zoomcar
 * @author Rohil
 * Copyright (c) 2024 Zoomcar. All rights reserved.
 */

@HiltViewModel
class TradingViewModel @Inject constructor(
    private val repository: TradingRepository,
    private val useCase: TradingUseCase
) : MviViewModel<TradingUiState, TradingEvent, TradingEffect>(TradingUiState()) {

    init {
        //Show loader initially
        setEffect(TradingEffect.ShowLoader)

        setEffect(TradingEffect.FetchDataFromApi)
    }

    override suspend fun handleEvents(event: TradingEvent) {
        when(event){
            TradingEvent.BackPressed -> {

            }
        }
    }

    override suspend fun handleEffects(effect: TradingEffect) {
        when(effect){
            TradingEffect.ShowLoader -> {
                updateState {
                    copy(
                        showLoader = true,
                        showError = false,
                        isOffline = false
                    )
                }
            }

            TradingEffect.FetchDataFromApi -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.getData { data, isFromPref ->
                        if (data != null) {
                            val task = async {
                                useCase.toUiModel(data)
                            }
                            viewModelScope.launch {
                                val uiModel = task.await()

                                updateState {
                                    copy(
                                        response = uiModel,
                                        showLoader = false,
                                        showError = false,
                                        isOffline = isFromPref == true
                                    )
                                }
                            }
                        } else {
//                            No data found, show error
                            setEffect(TradingEffect.ShowError)

                        }

                    }
                }
            }

            TradingEffect.ShowError -> {
                updateState {
                    TradingUiState(
                        showError = true
                    )
                }
            }
            TradingEffect.UpdateBottomSheetData -> {}
            is TradingEffect.UpdateMainData -> {}
        }
    }

}

data class TradingUiState(
    val response: ResponseUIVO? = null,
    val isOffline: Boolean = false,
    val showLoader: Boolean = false,
    val showError: Boolean = false
) : MviState

sealed interface TradingEffect : MviEffect {
    data object ShowLoader : TradingEffect
    data object ShowError : TradingEffect
    data object FetchDataFromApi : TradingEffect
    data object UpdateBottomSheetData : TradingEffect
    data class UpdateMainData(val isOffline: Boolean = false) : TradingEffect
}

sealed interface TradingEvent : MviEvent {
    data object BackPressed : TradingEvent
}