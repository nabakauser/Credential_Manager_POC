package com.example.credentialmanagerpoc.login.ui

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.credentialmanagerpoc.manager.AccountManager
import com.example.credentialmanagerpoc.manager.SignUpResult
import kotlinx.coroutines.launch

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        userName = "",
        password = "",
        toastMessage = "",
        isRegistered = true,
        onUserNameEntered = {},
        onPasswordEntered = {},
        onRegistered = {},
        onSignedIn = {},
    )
}

@Composable
fun LoginScreen(
    userName: String,
    password: String,
    isRegistered: Boolean,
    toastMessage: String,
    onUserNameEntered:(String) -> Unit,
    onPasswordEntered:(String) -> Unit,
    onRegistered:(Boolean) -> Unit,
    onSignedIn:(SignUpResult) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }
    LaunchedEffect(toastMessage) {
        if(toastMessage.isNotEmpty()) Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFD2D6EC),
            )
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if(isRegistered) "Register" else "Login",
                fontSize = 16.sp
            )
            Switch(
                checked = isRegistered,
                onCheckedChange = {
                    onRegistered(it)
                }
            )
        }
        UserNamePasswordComponent(
            title = "User Name",
            icon = Icons.Outlined.Person,
            text = userName,
            onTextEntered = onUserNameEntered,
        )
        UserNamePasswordComponent(
            title = "Password",
            icon = Icons.Outlined.Lock,
            text = password,
            onTextEntered = onPasswordEntered,
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                if(isRegistered) {
                    scope.launch {
                        val result = accountManager.signUpUser(
                            username = userName,
                            password = password,
                        )
                        onSignedIn(result)
                    }
                }
            }
        ) {
            Text(
                text = if(isRegistered) "Register" else "Login",
            )
        }
    }
}

@Composable
fun UserNamePasswordComponent(
    title: String,
    icon: ImageVector,
    text: String,
    onTextEntered: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = title
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { onTextEntered(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor =  Color.Transparent,
                unfocusedBorderColor =  Color.LightGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            )
        )
    }
}