package com.boniface.wapishicateringservice

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("Order", "Orders")
    BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        "Order" -> Icon(Icons.Default.Add, contentDescription = null)
                        "Orders" -> Icon(Icons.Default.List, contentDescription = null)
                    }
                },
                label = { Text(screen) },
                selected = navController.currentBackStackEntryAsState().value?.destination?.route == screen,
                onClick = {
                    navController.navigate(screen) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
