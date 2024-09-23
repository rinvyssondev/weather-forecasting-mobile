data class Forecast(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind,
    val rain: Rain
)

data class Clouds(
    val all: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: String,
    val temp_max: String,
    val temp_min: String,
    val sea_level: Int,
    val grnd_level: Int
)

data class Sys(
    val type: Int,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val country: String
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Wind(
    val deg: Double,
    val gust: Double,
    val speed: Double
)

data class Rain(
    val `1h`: Double,
)