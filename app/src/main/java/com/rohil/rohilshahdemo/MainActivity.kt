package com.rohil.rohilshahdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rohil.rohilshahdemo.trading.view.TradingView
import com.rohil.rohilshahdemo.trading.viewmodel.TradingEvent
import com.rohil.rohilshahdemo.trading.viewmodel.TradingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: TradingViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TradingView(
                viewModel = viewModel
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    when(event){
                        //Handle back pressed event
                        TradingEvent.BackPressed -> {
                            finish()
                        }
                    }
                }
            }
        }
    }
}