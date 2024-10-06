package com.solodev.skycast.presentation.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
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
import com.solodev.skycast.domain.model.cities
import com.solodev.skycast.presentation.features.auth.AuthState
import com.solodev.skycast.presentation.features.auth.AuthViewModel
import com.solodev.skycast.presentation.features.home.components.CityItem
import com.solodev.skycast.presentation.features.home.components.SkyCastTopBar
import com.solodev.skycast.presentation.features.home.components.WeatherContent
import com.solodev.skycast.presentation.navigation.LoginRoute

@Composable
fun HomeScreen(navController: NavController) {

    val authViewModel = hiltViewModel<AuthViewModel>()
    val authState = authViewModel.authState.observeAsState()

    val homeViewModel: HomeViewModel = hiltViewModel()

    val weatherState by homeViewModel.weatherState.collectAsState()
    val forecastState by homeViewModel.forecastState.collectAsState()



    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate(LoginRoute)
            else -> Unit
        }
    }

    LaunchedEffect(key1 = Unit) {
        // Initially load weather for the first city
        val initialCity = cities.first()
        homeViewModel.getWeatherData(latitude = initialCity.latitude, longitude = initialCity.longitude)
        homeViewModel.getWeatherForecast(latitude = initialCity.latitude, longitude = initialCity.longitude)
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SkyCastTopBar(
                onSignOutClick = {
                authViewModel.signOut()
                })
        }
    ) { innerPadding ->

        WeatherContent(
            weatherState = weatherState,
            forecastState = forecastState,
            paddingValues = innerPadding,
            cityOnClicked = { city ->
                homeViewModel.getWeatherData(latitude = city.latitude, longitude = city.longitude)
                homeViewModel.getWeatherForecast(latitude = city.latitude, longitude = city.longitude)
            })
    }

}
