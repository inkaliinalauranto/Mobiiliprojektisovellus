package com.example.coolbox_mobiiliprojekti_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coolbox_mobiiliprojekti_app.ui.theme.CoolBoxmobiiliprojektiAppTheme
import com.example.coolbox_mobiiliprojekti_app.view.ConsumptionScreen
import com.example.coolbox_mobiiliprojekti_app.view.LoginScreen
import com.example.coolbox_mobiiliprojekti_app.view.MainScreen
import com.example.coolbox_mobiiliprojekti_app.view.PanelsScreen
import com.example.coolbox_mobiiliprojekti_app.view.ProductionScreen
import com.example.coolbox_mobiiliprojekti_app.view.ThemesScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoolBoxmobiiliprojektiAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    ModalNavigationDrawer(
                        gesturesEnabled = (
                                navBackStackEntry?.destination?.route != "loginScreen" &&
                                        navBackStackEntry?.destination?.route != "registerScreen"
                                ),
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(20.dp))

                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    ,
                                    label = { Text(text = "Home") },
                                    selected = navBackStackEntry?.destination?.route == "mainScreen",
                                    onClick = {
                                        navController.navigate("mainScreen") {
                                            launchSingleTop = true
                                        }
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Home,
                                            contentDescription = "Home"
                                        )
                                    }
                                )
                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    ,
                                    label = { Text(text = "Panels") },
                                    selected = navBackStackEntry?.destination?.route == "panelsScreen",
                                    onClick = {
                                        navController.navigate("panelsScreen") {
                                            launchSingleTop = true
                                        }
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.DashboardCustomize,
                                            contentDescription = "Panels"
                                        )
                                    }
                                )
                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    ,
                                    label = { Text(text = "Themes") },
                                    selected = navBackStackEntry?.destination?.route == "themesScreen",
                                    onClick = {
                                        navController.navigate("themesScreen") {
                                            launchSingleTop = true
                                        }
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.ColorLens,
                                            contentDescription = "Themes"
                                        )
                                    }
                                )
                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                    ,
                                    label = { Text(text = "Logout") },
                                    selected = navBackStackEntry?.destination?.route == "loginScreen",
                                    onClick = {
                                        navController.navigate("loginScreen")
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.LockOpen,
                                            contentDescription = "Login"
                                        )
                                    }
                                )
                            }
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "mainScreen"
                        ) {
                            composable(route = "loginScreen") {
                                LoginScreen(
                                    onLoginClick = {
                                        navController.navigate("mainScreen")
                                    }
                                )
                            }
                            composable("mainScreen") {
                                MainScreen(
                                    onMenuClick = {
                                        scope.launch { drawerState.open() }
                                    },
                                    gotoConsumption = {
                                        navController.navigate("consumptionScreen") {
                                            launchSingleTop = true
                                        }
                                    },
                                    gotoProduction = {
                                        navController.navigate("productionScreen") {
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                            composable("consumptionScreen") {
                                ConsumptionScreen(
                                    goBack = {
                                        navController.navigateUp()
                                    },
                                    onMenuClick = {
                                        scope.launch { drawerState.open() }
                                    }
                                )
                            }
                            composable("productionScreen") {
                                ProductionScreen(
                                    goBack = {
                                        navController.navigateUp()
                                    },
                                    onMenuClick = {
                                        scope.launch { drawerState.open() }
                                    }
                                )
                            }
                            composable("panelsScreen") {
                                PanelsScreen(
                                    goBack = {
                                        navController.navigateUp()
                                    },
                                    onMenuClick = {
                                        scope.launch { drawerState.open() }
                                    }
                                )
                            }
                            composable("themesScreen") {
                                ThemesScreen(
                                    goBack = {
                                        navController.navigateUp()
                                    },
                                    onMenuClick = {
                                        scope.launch { drawerState.open() }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
