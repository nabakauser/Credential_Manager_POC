package com.example.credentialmanagerpoc.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.credentialmanagerpoc.loggedIn.LoggedInScreen
import com.example.credentialmanagerpoc.login.LoginViewModel
import com.example.credentialmanagerpoc.login.ui.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data class LoggedInRoute(val userName: String)

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginRoute()
        }
        
        composable<LoggedInRoute> { 
            LoggedInScreen(user = it.toRoute<LoggedInRoute>().userName)
        }
    }
}