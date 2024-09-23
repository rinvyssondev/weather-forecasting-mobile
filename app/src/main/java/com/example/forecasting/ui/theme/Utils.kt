package com.example.forecasting.ui.theme

class Utils {
    companion object {

        fun buildIcon(icon: String, isBigSize: Boolean): String {
            return if (isBigSize) {
                "https://openweathermap.org/img/wn/$icon@4x.png"
            } else {
                "https://openweathermap.org/img/wn/$icon.png"
            }
        }
    }
}