package com.example.forecasting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.forecasting.viewmodel.ForecastingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                ForecastingApp(viewModel = ForecastingViewModel())
        }
    }
}