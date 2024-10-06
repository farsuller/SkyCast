package com.solodev.skycast.presentation.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.solodev.skycast.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkyCastTopBar(onSignOutClick:()-> Unit) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red),
        navigationIcon = {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = R.drawable.cloud_circle),
                contentDescription = "Logo Image",
                contentScale = ContentScale.Crop,
            )
        },
        title = {
            Text(
                text = "SkyCast",
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }, actions = {
            TextButton(onClick = onSignOutClick) {
                Text(text = "Sign out")
            }
        })
}