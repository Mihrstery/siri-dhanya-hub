package com.example.nithaiproject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Mandi : Screen("mandi", "Mandi Watch", Icons.Default.TrendingUp)
    object Recipes : Screen("recipes", "Recipe Lab", Icons.Default.RestaurantMenu)
    object Connect : Screen("connect", "FPO Connect", Icons.Default.Groups)
    object Profile : Screen("profile", "My Wellness", Icons.Default.Person)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Mandi,
    Screen.Recipes,
    Screen.Connect
)
