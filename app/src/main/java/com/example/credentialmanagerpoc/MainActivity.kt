package com.example.credentialmanagerpoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.credentialmanagerpoc.navigation.NavigationRoot
import com.example.credentialmanagerpoc.ui.theme.CredentialManagerPOCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CredentialManagerPOCTheme {
                NavigationRoot()
            }
        }
    }
}