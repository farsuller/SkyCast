package com.solodev.skycast.presentation.features.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solodev.skycast.R
import com.solodev.skycast.domain.model.Clouds
import com.solodev.skycast.domain.model.Main
import com.solodev.skycast.domain.model.Weather
import com.solodev.skycast.domain.model.Wind
import com.solodev.skycast.domain.model.forecast.Forecast
import com.solodev.skycast.domain.model.forecast.Rain
import com.solodev.skycast.domain.model.forecast.Sys
import com.solodev.skycast.presentation.features.home.state.ForecastState
import com.solodev.skycast.presentation.features.home.state.WeatherState
import com.solodev.skycast.ui.theme.Elevation
import com.solodev.skycast.ui.theme.SkyCastTheme
import com.solodev.skycast.utils.kelvinToCelsius

@SuppressLint("DefaultLocale")
@Composable
fun ForecastCard(
    forecast: Forecast
){

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = Elevation.level4
        ),
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp),
        shape = RoundedCornerShape(13.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) {


                    val formattedTemperature = String.format("%.2f", kelvinToCelsius(forecast.main.temp))
                    Text(
                        text = "Temperature: $formattedTemperature°C",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                        color = MaterialTheme.colorScheme.onSurface,
                        )

                    Text(
                        text = "Weather: ${forecast.weather.firstOrNull()?.main}",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Text(text = "Description: ${forecast.weather.firstOrNull()?.description
                        ?.split(" ")
                        ?.joinToString(" ")
                        { word -> word.replaceFirstChar { it.uppercase() }
                        } ?: "" }")


                }
                Icon(
                    modifier = Modifier.size(90.dp).align(Alignment.CenterVertically).weight(0.5f),
                    painter = painterResource(id = when(forecast.weather.firstOrNull()?.main){
                        "Clear" -> R.drawable.sunny
                        "Rain","Moderate Rain" -> R.drawable.rainy
                        "Clouds" -> R.drawable.cloud
                        "Partially cloudy" -> R.drawable.partly_cloudy
                        "Thunder" -> R.drawable.thunderstorm
                        else -> R.drawable.sunny
                    }),
                    contentDescription = "Logo Image",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                val formattedTemperature = String.format("%.2f", kelvinToCelsius(forecast.main.feelsLike))
                Text(text = "Feels Like: $formattedTemperature°C")
                Text(text = "Humidity: ${forecast.main.humidity}%")
                Text(text = "Wind Speed: ${forecast.wind.speed} m/s")
            }
        }
    }

}


@Preview
@Composable
private fun ForecastCardPreview(){
    SkyCastTheme {
        Surface {
            ForecastCard(forecast = Forecast(
                clouds = Clouds(all = 0),
                dt = 0,
                dateTxt = "",
                main = Main(
                    feelsLike = 0.0,
                    groundLevel = 0,
                    humidity = 0,
                    pressure = 0,
                    seaLevel = 0,
                    temp = 0.0,
                    tempMax = 0.0,
                    tempMin = 0.0
                ),
                pop = 0.0,
                rain = Rain(rain3h = 0.0),
                sys = Sys(pod = ""),
                visibility = 0,
                weather = listOf(),
                wind = Wind(
                    deg = 0,
                    gust = 0.0,
                    speed = 0.0
                ),
            ))
        }
    }
}