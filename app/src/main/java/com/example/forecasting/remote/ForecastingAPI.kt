package com.example.forecasting.remote

import Forecast
import com.example.forecasting.remote.DTO.forecast5.Forecast5

import retrofit2.http.GET
import retrofit2.http.Path

interface ForecastingAPI {
    @GET("weather/city={city}")
    suspend fun getForecast(@Path("city") city: String): Forecast

    @GET("forecast5/city={city}")
    suspend fun getForecast5(@Path("city") city: String): Forecast5

}