package com.example.forecasting.remote.DTO.forecast5

data class Forecast5(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastList>,
    val message: Int
)