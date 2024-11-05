package com.example.credentialmanagerpoc.login

import androidx.lifecycle.ViewModel
import com.example.credentialmanagerpoc.manager.SignUpResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    private val loginViewModelState = MutableStateFlow(LoginState())
    val loginUiState = loginViewModelState.asStateFlow()

    fun onUserNameEntered(name: String) {
        loginViewModelState.update {
            it.copy(
                userName = name
            )
        }
    }

    fun onPasswordEntered(password: String) {
        loginViewModelState.update {
            it.copy(
                password = password
            )
        }
    }

    fun onRegistered(isRegistered: Boolean) {
        loginViewModelState.update {
            it.copy(
                isRegistered = isRegistered
            )
        }
    }

    fun onSignedIn(result: SignUpResult) {
        when(result) {
            is SignUpResult.Success -> {
                loginViewModelState.update {
                    it.copy(
                        message = result.username
                    )
                }
            }
            is SignUpResult.Cancelled -> {
                loginViewModelState.update {
                    it.copy(
                        message = result.errorMsg
                    )
                }

            }
            is SignUpResult.Failure -> {
                loginViewModelState.update {
                    it.copy(
                        message = result.errorMsg
                    )
                }
            }
        }
    }
}

data class LoginState(
    val userName: String = "",
    val password: String = "",
    val isRegistered: Boolean = true,
    val message: String? = ""
)