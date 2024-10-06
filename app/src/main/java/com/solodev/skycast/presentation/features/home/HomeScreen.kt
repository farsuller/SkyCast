package com.solodev.skycast.presentation.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solodev.skycast.presentation.features.auth.AuthState
import com.solodev.skycast.presentation.features.auth.AuthViewModel
import com.solodev.skycast.presentation.navigation.LoginRoute

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {

    val authViewModel = hiltViewModel<AuthViewModel>()
    val authState = authViewModel.authState.observeAsState()

    val homeViewModel: HomeViewModel = hiltViewModel()
    val weatherState by homeViewModel.weatherState.collectAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate(LoginRoute)
            else -> Unit
        }
    }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when {
            weatherState.isLoading -> {
                CircularProgressIndicator()
            }

            weatherState.errorMessage != null -> {
                Text(text = "Error: ${weatherState.errorMessage}")

                TextButton(onClick = {
                    authViewModel.signOut()
                }) {
                    Text(text = "Sign out")
                }
            }

            else -> {
                Column(modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "City: ${weatherState.cityName}")
                    Text(text = "Temperature: ${weatherState.temperature}°C")
                    Text(text = "Feels like: ${weatherState.feelsLike}°C")
                    Text(text = "Min Temp: ${weatherState.tempMin}°C")
                    Text(text = "Max Temp: ${weatherState.tempMax}°C")
                    Text(text = "Weather: ${weatherState.weatherDescription}")
                    Text(text = "Humidity: ${weatherState.humidity}%")
                    Text(text = "Wind Speed: ${weatherState.windSpeed} m/s")
                    Text(text = "Cloudiness: ${weatherState.cloudiness}%")

                    TextButton(onClick = {
                        authViewModel.signOut()
                    }) {
                        Text(text = "Sign out")
                    }
                }

            }
        }

    }

}