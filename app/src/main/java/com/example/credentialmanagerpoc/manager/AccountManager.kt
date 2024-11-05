package com.example.credentialmanagerpoc.manager

import android.app.Activity
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException

class AccountManager(
    private val activity: Activity
) {
    private val credentialManager = CredentialManager.create(activity)

    suspend fun signUpUser(
        username: String,
        password: String,
    ): SignUpResult {
        return try {
            credentialManager.createCredential(
                context = activity,
                request = CreatePasswordRequest(
                    id = username,
                    password = password,
                )
            )
            SignUpResult.Success(username)
        } catch (e: CreateCredentialCancellationException) {
            SignUpResult.Cancelled(e.message)
        } catch (e: CreateCredentialException) {
            SignUpResult.Failure(e.message)
        } catch (e: Exception) {
            SignUpResult.Failure(e.message)
        }
    }

    suspend fun signIn(): SignInResult {
        return try {
            val response = credentialManager.getCredential(
                context = activity,
                request = GetCredentialRequest(
                    credentialOptions = listOf(GetPasswordOption())
                )
            )
            val credentials = response.credential as? PasswordCredential
                ?: return SignInResult.Failure("Sign-In Failed!")
            SignInResult.Success(credentials.id)
        } catch (e: GetCredentialCancellationException) {
            SignInResult.Cancelled(e.message)
        } catch (e: NoCredentialException) {
            SignInResult.NoCredentials(e.message)
        } catch (e: GetCredentialException) {
            SignInResult.Failure(e.message)
        }

    }
}

sealed interface SignUpResult {
    data class Success(val username: String): SignUpResult
    data class Cancelled(val errorMsg: String?): SignUpResult
    data class Failure(val errorMsg: String?): SignUpResult
}

sealed interface SignInResult {
    data class Success(val username: String): SignInResult
    data class Cancelled(val errorMsg: String?): SignInResult
    data class Failure(val errorMsg: String?): SignInResult
    data class NoCredentials(val errorMsg: String?): SignInResult
}