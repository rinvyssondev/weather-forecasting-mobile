package com.example.forecasting.viewmodel

import Forecast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecasting.remote.DTO.forecast5.Forecast5
import com.example.forecasting.remote.DTO.forecast5.ForecastList
import com.example.forecasting.remote.DTO.forecast5.Weather
import com.example.forecasting.repository.ForecastingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ForecastingViewModel: ViewModel() {

    private val respository = ForecastingRepository()

    private val _forecast = MutableLiveData<Forecast?>()
    val forecast: MutableLiveData<Forecast?> = _forecast

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: MutableLiveData<String?> = _errorMessage

    private val _forecast5 = MutableLiveData<Forecast5?>()
    val forecast5: MutableLiveData<Forecast5?> = _forecast5

    fun fetchForecast(city: String) {
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
//                val response = respository.getForecast(city)
//                _forecast.postValue(response)

                try {
                    val response = respository.getForecast(city)
                    _forecast.postValue(response)
                    _errorMessage.postValue(null)
                } catch (e: Exception) {
                    if (e is HttpException && e.code() == 500) {
                        _forecast.postValue(null) // ou Forecast() se você tiver um objeto de previsão vazio
                        _errorMessage.postValue("City not found")
                    }
                    _errorMessage.postValue("City not found")
                }
            }
        }
    }

    fun fetchForecast5(city: String) {
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = respository.getForecast5(city)
                    _errorMessage.postValue(null)
                    _forecast5.postValue(response)
                } catch (e: Exception) {
                    if (e is HttpException && e.code() == 500) {
                        _forecast5.postValue(null) // ou Forecast5() se você tiver um objeto de previsão vazio
                        _errorMessage.postValue("City not found")
                    }
                    _errorMessage.postValue("City not found")
                }
            }
        }
    }

}
