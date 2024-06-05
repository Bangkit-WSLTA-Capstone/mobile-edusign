package com.nekkiichi.edusign.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Scaffold { paddingScaffold ->
        Column(Modifier.padding(paddingScaffold)) {
            Column(
                Modifier
                    .defaultMinSize(minHeight = 200.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Welcome Back!", style = MaterialTheme.typography.headlineLarge)
                Text(text = "Let's Sign in with your account!")
            }
            LoginForm(Modifier.padding(horizontal = 16.dp, vertical = 24.dp))
        }
    }
}

@Composable
private fun LoginForm(modifier: Modifier = Modifier) {
    var email by remember {
        mutableStateOf("")
    }
    val password by remember {
        mutableStateOf("")
    }
    Column(Modifier.then(modifier)) {

        OutlinedTextField(
            email,
            onValueChange = { email = it },
            Modifier.fillMaxWidth(),
            label = { Text(text = "Username") },
        )
        OutlinedTextField(
            password,
            onValueChange = { email = it },
            Modifier.fillMaxWidth(),
            label = { Text(text = "Username") },
        )
    }
}


@Preview
@Composable
private fun LoginScreenPreview(modifier: Modifier = Modifier) {
    EduSignTheme(darkTheme = true) {
        Surface {
            LoginScreen()
        }
    }
}