package com.solodev.skycast.presentation.features.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.solodev.skycast.presentation.features.home.state.ForecastState
import com.solodev.skycast.presentation.features.home.state.WeatherState
import com.solodev.skycast.presentation.features.home.util.Tabs

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
                contentAlignment = Alignment.Center
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
                                                .padding(top = paddingValues.calculateTopPadding()),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            WeatherCard(weatherState = weatherState)
                                        }
                                    }
                                }

                            }
                            Tabs.WEATHERS -> {
                                when {
                                    forecastState.isLoading -> {
                                        // Show loading indicator
                                        CircularProgressIndicator()
                                    }
                                    forecastState.error != null -> {
                                        // Show error message
                                        Text(text = forecastState.error)
                                    }
                                    forecastState.forecast != null -> {
                                        // Show forecast data
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = paddingValues.calculateTopPadding()),
                                        ){
                                            item {
                                                Text(
                                                    modifier = Modifier.padding(horizontal = 10.dp),
                                                    text = "${forecastState.city?.name}, ${forecastState.city?.country}")
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