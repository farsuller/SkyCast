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
import com.solodev.skycast.presentation.features.home.state.WeatherState
import com.solodev.skycast.ui.theme.Elevation
import com.solodev.skycast.ui.theme.SkyCastTheme
import com.solodev.skycast.utils.kelvinToCelsius

@SuppressLint("DefaultLocale")
@Composable
fun WeatherCard(
    weatherState: WeatherState
){

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = Elevation.level4
        ),
        modifier = Modifier
            .fillMaxWidth()
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


                    Text(
                        text = "Now",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                        color = MaterialTheme.colorScheme.onSurface,)

                    val formattedTemperature = String.format("%.2f", kelvinToCelsius(weatherState.temperature))
                    Text(text = "Temperature: $formattedTemperature°C")

                    Text(text = "Weather: ${weatherState.weatherDescription
                        ?.split(" ")
                        ?.joinToString(" ")
                        { word -> word.replaceFirstChar { it.uppercase() }
                        } ?: "" }")
                }

                Image(
                    modifier = Modifier.size(120.dp).align(Alignment.CenterVertically).weight(0.5f),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo Image",
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                val formattedTemperature = String.format("%.2f", kelvinToCelsius(weatherState.feelsLike))
                Text(text = "Feels Like: $formattedTemperature°C")
                Text(text = "Humidity: ${weatherState.humidity}%")
                Text(text = "Wind Speed: ${weatherState.windSpeed} m/s")
            }
        }
    }

}


@Preview
@Composable
private fun WeatherCardPreview(){
    SkyCastTheme {
        Surface {
            WeatherCard(weatherState = WeatherState())
        }
    }
}