package com.example.forecasting

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.forecasting.components.ButtonDate
import com.example.forecasting.components.RowText
import com.example.forecasting.components.RowTextImage
import com.example.forecasting.ui.theme.Blue100
import com.example.forecasting.ui.theme.Blue50
import com.example.forecasting.ui.theme.Blue75
import com.example.forecasting.ui.theme.Purple25
import com.example.forecasting.ui.theme.Purple50
import com.example.forecasting.ui.theme.Utils
import com.example.forecasting.viewmodel.ForecastingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ForecastingApp(viewModel: ForecastingViewModel) {

    val forecast by viewModel.forecast.observeAsState()
    val forecast5 by viewModel.forecast5.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()
    val context = LocalContext.current

    var city by remember { mutableStateOf("") }

    var selectedDate = remember { mutableStateOf("") }


    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth()
        .background(
            Brush.verticalGradient(
                colors = listOf(
                    Blue100,
                    Blue75,
                    Blue50,
                    Purple50,
                    Purple25,
                ),
            )
        )) {

        Column {

            TextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Purple50,
                    focusedContainerColor = Purple25,
                    focusedTextColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Location",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            viewModel.fetchForecast(city)
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(8000L)
                            }
                            viewModel.fetchForecast5(city)
                        }
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Purple50
                )
            ) {

                RowText(text = "${forecast?.name ?: ""}")

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(forecast?.weather?.getOrNull(0)?.icon?.run { Utils.buildIcon(this.toString(), true) })
                            .build(),
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(150.dp),
                    )
                }

                RowText(text = "${forecast?.main?.temp ?: "" }")

                Spacer(modifier = Modifier.height(4.dp))

                RowText(text = "${forecast?.weather?.get(0)?.description ?: ""}")

                if (forecast?.weather?.get(0)?.description != null) {
                    RowText(text = "Max.:${forecast?.main?.temp_max ?: ""}     Min.:${forecast?.main?.temp_min ?: ""}")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

                    if (forecast?.weather?.get(0)?.description != null) {

                        RowTextImage(
                            text = "Wind",
                            painterResource = R.drawable.wind,
                            contentDescription = "wind",
                            textApi = "${forecast?.wind?.speed ?: ""} m/s"
                        )

                        RowTextImage(
                            text = "Humidity",
                            painterResource = R.drawable.humidity,
                            contentDescription = "humidity",
                            textApi = "${forecast?.main?.humidity ?: ""} %"
                        )

                        RowTextImage(
                            text = "Pressure",
                            painterResource = R.drawable.pressure_2,
                            contentDescription = "pressure",
                            textApi = "${forecast?.main?.pressure ?: ""} hPa"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Purple50,
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                ) {
                    Row {

                        Text(
                            text = "Forecast - 5 days",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        ButtonDate(selectedDate = selectedDate)

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        forecast5?.list?.let { forecastList ->
                            forecastList.filter { it.dt_txt.split(" ")[0] == selectedDate.value }
                                .map { it.dt_txt.split(" ")[1] }
                                .forEach {
                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "${it.split(":")[0]}h",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .width(35.dp)
                                            .height(20.dp)
                                    )
                                }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    forecast5?.list?.let { forecastList ->
                        forecastList.filter { it.dt_txt.split(" ")[0] == selectedDate.value }
                            .flatMap { it.weather.map { weather -> weather.icon } }
                            .forEach {
                                Spacer(modifier = Modifier.width(8.dp))

                                AsyncImage(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .data(it.run { Utils.buildIcon(this, true) })
                                        .build(),
                                    contentDescription = "Weather Icon",
                                    alignment = Alignment.Center,
                                    modifier = Modifier.size(35.dp),
                                )
                            }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    forecast5?.list?.let { forecastList ->
                        forecastList.filter { it.dt_txt.split(" ")[0] == selectedDate.value }
                            .map { it.main.temp.toInt() }
                            .forEach {
                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "$it ºC",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    modifier = Modifier.size(35.dp)
                                )
                            }
                    }
                }
            }
        }
    }
}