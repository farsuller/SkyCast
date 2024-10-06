package com.solodev.skycast.presentation.features.home.components

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.solodev.skycast.R
import com.solodev.skycast.domain.model.City
import com.solodev.skycast.domain.model.cities
import com.solodev.skycast.presentation.features.home.state.ForecastState
import com.solodev.skycast.presentation.features.home.state.WeatherState
import com.solodev.skycast.presentation.features.home.util.Tabs
import com.solodev.skycast.utils.convertUnixToReadableTime

@SuppressLint("DefaultLocale")
@Composable
fun WeatherContent(
    paddingValues: PaddingValues,
    weatherState: WeatherState,
    forecastState: ForecastState,
    cityOnClicked : (City) -> Unit
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
                                    weatherState.isLoading -> ContentLoading()
                                    weatherState.errorMessage != null -> ContentError(weatherState.errorMessage)

                                    else -> {
                                        val sunriseText = convertUnixToReadableTime(weatherState.sunrise, weatherState.timezone)
                                        val sunsetText = convertUnixToReadableTime(weatherState.sunset, weatherState.timezone)

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
                                            Text(text = "Area: ${weatherState.cityName}, ${weatherState.country}")

                                            Spacer(modifier = Modifier.height(20.dp))

                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Row(modifier = Modifier.fillMaxWidth()) {
                                                    SunIconColumn(modifier = Modifier.weight(1F), label = "Sunrise", timeText = sunriseText, icon = R.drawable.sunrise)
                                                    SunIconColumn(modifier = Modifier.weight(1F),label = "Sunset", timeText = sunsetText, icon = R.drawable.sunset)
                                                }
                                            }

                                            WeatherCard(weatherState = weatherState)

                                            Spacer(modifier = Modifier.height(20.dp))

                                            LazyRow(
                                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                                            ) {
                                                items(cities) { city ->
                                                    CityItem(city = city, onClick = cityOnClicked)
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                            Tabs.WEATHERS -> {
                                when {
                                    forecastState.isLoading -> ContentLoading()
                                    forecastState.error != null -> ContentError(forecastState.error)

                                    forecastState.forecast != null -> {

                                        val forecastSunrise = convertUnixToReadableTime(
                                            forecastState.city?.sunrise ?: 0,
                                            forecastState.city?.timezone ?: 0)

                                        val forecastSunset = convertUnixToReadableTime(
                                            forecastState.city?.sunset ?: 0,
                                            forecastState.city?.timezone ?: 0)



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
                                                        text = " Area: ${forecastState.city?.name}, ${forecastState.city?.country}")

                                                    Spacer(modifier = Modifier.height(20.dp))

                                                    Row(modifier = Modifier.fillMaxWidth()) {
                                                        Row(modifier = Modifier.fillMaxWidth()) {
                                                            SunIconColumn(modifier = Modifier.weight(1F), label = "Sunrise", timeText = forecastSunrise, icon = R.drawable.sunrise)
                                                            SunIconColumn(modifier = Modifier.weight(1F),label = "Sunset", timeText = forecastSunset, icon = R.drawable.sunset)
                                                        }
                                                    }

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





