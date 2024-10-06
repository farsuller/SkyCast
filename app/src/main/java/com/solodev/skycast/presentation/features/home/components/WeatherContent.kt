package com.solodev.skycast.presentation.features.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solodev.skycast.R
import com.solodev.skycast.presentation.features.auth.AuthState
import com.solodev.skycast.presentation.features.auth.AuthViewModel
import com.solodev.skycast.presentation.features.home.WeatherState
import com.solodev.skycast.presentation.features.home.components.Tabs
import com.solodev.skycast.presentation.navigation.LoginRoute
import com.solodev.skycast.utils.kelvinToCelsius

@SuppressLint("DefaultLocale")
@Composable
fun WeatherContent(
    weatherState: WeatherState
) {

        var selectedItem by remember { mutableIntStateOf(0) }
        NavigationSuiteScaffold(
            modifier = Modifier.fillMaxSize(),
            navigationSuiteItems = {
                Tabs.entries.forEachIndexed { index, screen ->
                    item(
                        selected = index == selectedItem,
                        onClick = { selectedItem = index },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title
                            )
                        },
                        label = { Text(screen.title) }

                    )

                }
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Tabs.entries.forEachIndexed { index, screen ->
                    if (index == selectedItem) {

                        when(screen){
                            Tabs.HOME -> {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "City: ${weatherState.cityName}")
                                    val formattedTemperature = String.format("%.2f", kelvinToCelsius(weatherState.temperature))
                                    Text(text = "Temperature: $formattedTemperature°C")

                                    Text(text = "Weather: ${weatherState.weatherDescription
                                        ?.split(" ")
                                        ?.joinToString(" ") 
                                        { word -> word.replaceFirstChar { it.uppercase() }
                                    }}")
                                    Text(text = "Humidity: ${weatherState.humidity}%")
                                    Text(text = "Wind Speed: ${weatherState.windSpeed} m/s")
                                    Text(text = "Cloudiness: ${weatherState.cloudiness}%")
                                }
                            }
                            Tabs.WEATHERS -> {
                                Text(text = "Search")
                            }
                        }


                    }
                }
            }
        }
    }