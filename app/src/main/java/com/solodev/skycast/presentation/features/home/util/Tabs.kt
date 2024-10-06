package com.solodev.skycast.presentation.features.home.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.ui.graphics.vector.ImageVector

enum class Tabs(val title: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.CloudQueue),
    WEATHERS("Weathers", Icons.Default.Cloud),
}