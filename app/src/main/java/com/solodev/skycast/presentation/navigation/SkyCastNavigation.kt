package com.solodev.skycast.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.solodev.skycast.presentation.features.auth.login.LoginScreen
import com.solodev.skycast.presentation.features.auth.signup.SignUpScreen
import com.solodev.skycast.presentation.features.home.HomeScreen

@Composable
fun SkyCastNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LoginRoute){
        composable<LoginRoute>{
            LoginScreen(modifier,navController)
        }
        composable<SignUpRoute>{
            SignUpScreen(modifier,navController)
        }
        composable<HomeRoute>{
            HomeScreen(modifier,navController)
        }
    }
}