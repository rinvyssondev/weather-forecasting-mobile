package com.example.forecasting.repository

import com.example.forecasting.remote.ApiClient

class ForecastingRepository() {

    private val repository = ApiClient.apiService

    suspend fun getForecast(city: String) = repository.getForecast(city)

    suspend fun getForecast5(city: String) = repository.getForecast5(city)

}