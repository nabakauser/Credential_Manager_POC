package com.example.credentialmanagerpoc.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.credentialmanagerpoc.login.LoginViewModel

@Composable
fun LoginRoute() {
    val viewModel = viewModel<LoginViewModel>()
    val uiState by viewModel.loginUiState.collectAsState()

    LoginScreen(
        userName = uiState.userName,
        password = uiState.password,
        toastMessage = uiState.message ?: "est",
        isRegistered = uiState.isRegistered,
        onUserNameEntered = { viewModel.onUserNameEntered(it) },
        onPasswordEntered = { viewModel.onPasswordEntered(it) },
        onRegistered = { viewModel.onRegistered(it) },
        onSignedIn = { viewModel.onSignedIn(it) },
    )
}