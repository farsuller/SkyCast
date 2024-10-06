package com.solodev.skycast.presentation.features.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.solodev.skycast.R
import com.solodev.skycast.presentation.features.home.state.ForecastState
import com.solodev.skycast.presentation.features.home.state.WeatherState
import com.solodev.skycast.presentation.features.home.util.Tabs
import com.solodev.skycast.utils.convertUnixToReadableTime

@SuppressLint("DefaultLocale")
@Composable
fun WeatherContent(
    paddingValues: PaddingValues,
    weatherState: WeatherState,
    forecastState: ForecastState
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
                contentAlignment = Alignment.TopStart
            ) {
                Tabs.entries.forEachIndexed { index, screen ->
                    if (index == selectedItem) {

                        when(screen){
                            Tabs.HOME -> {

                                when {
                                    weatherState.isLoading -> {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }

                                    }

                                    weatherState.errorMessage != null -> {

                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(modifier = Modifier.matchParentSize()) {
                                                Text(text = "Error: ${weatherState.errorMessage}")
                                            }
                                        }
                                    }
                                    else -> {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    top = paddingValues.calculateTopPadding(),
                                                    start = 10.dp,
                                                    end = 10.dp),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.Top
                                        ) {
                                            Text(text = "City: ${weatherState.cityName}, ${weatherState.country}")

                                            Spacer(modifier = Modifier.height(20.dp))

                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Column(modifier = Modifier.weight(1f),
                                                    horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Text(text = "Sunrise: ${convertUnixToReadableTime(weatherState.sunrise, weatherState.timezone)}")
                                                    Image(
                                                        modifier = Modifier.size(50.dp),
                                                        painter = painterResource(id = R.drawable.cloud_circle),
                                                        contentDescription = "Logo Image",
                                                        contentScale = ContentScale.Crop,
                                                    )
                                                }

                                                Column(modifier = Modifier.weight(1f),
                                                    horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Text(text = "Sunset: ${convertUnixToReadableTime(weatherState.sunset, weatherState.timezone)}")
                                                    Image(
                                                        modifier = Modifier.size(50.dp),
                                                        painter = painterResource(id = R.drawable.cloud_circle),
                                                        contentDescription = "Logo Image",
                                                        contentScale = ContentScale.Crop,
                                                    )
                                                }
                                            }



                                            WeatherCard(weatherState = weatherState)
                                        }
                                    }
                                }

                            }
                            Tabs.WEATHERS -> {
                                when {
                                    forecastState.isLoading -> {
                                        CircularProgressIndicator()
                                    }
                                    forecastState.error != null -> {
                                        Text(text = forecastState.error)
                                    }
                                    forecastState.forecast != null -> {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = paddingValues.calculateTopPadding()),
                                        ){
                                            item {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = Alignment.Start
                                                ) {
                                                    Text(
                                                        modifier = Modifier.padding(horizontal = 10.dp),
                                                        text = "${forecastState.city?.name}, ${forecastState.city?.country}")

                                                    Text(
                                                        modifier = Modifier.padding(horizontal = 10.dp),
                                                        text = "Sunrise ${convertUnixToReadableTime(forecastState.city?.sunrise ?: 0, 
                                                            forecastState.city?.timezone ?: 0)}")

                                                    Text(
                                                        modifier = Modifier.padding(horizontal = 10.dp),
                                                        text = "Sunset ${convertUnixToReadableTime(forecastState.city?.sunset ?: 0,
                                                            forecastState.city?.timezone ?: 0)}")

                                                }

                                            }
                                            items(forecastState.forecast) { forecast ->
                                                ForecastCard(forecast = forecast)
                                            }
                                        }
                                    }
                                }
                            }
                        }


                    }
                }
            }
        }
    }