package com.nekkiichi.edusign.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Login
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.ui.composable.OutlineButton
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.utils.isValidEmail
import com.nekkiichi.edusign.utils.isValidPassword

private data class LoginForm(val email: String, val password: String)

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var loginForm: LoginForm? by remember {
        mutableStateOf(null)
    }

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
            LoginForm(
                Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                onChange = { loginForm = it })
            Spacer(modifier = Modifier.weight(1f))
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
                    .widthIn(320.dp)
            ) {
                PrimaryButton(onCLick = {
                    //TODO: call viewmodel to check login
                }, Modifier.fillMaxWidth()) {
                    Text(text = "SIGN IN")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Rounded.Login, contentDescription = "")
                }
                HorizontalDivider(Modifier.padding(8.dp))
                OutlineButton(onCLick = {
                    navController.navigate("register")
                }, Modifier.fillMaxWidth()) {
                    Text(text = "CREATE ACCOUNT")
                }
                OutlineButton(onCLick = {
                    navController.navigate("dashboard")
                }, Modifier.fillMaxWidth()) {
                    Text(text = "SKIP ->")
                }
            }
        }
    }
}

@Composable
private fun LoginForm(modifier: Modifier = Modifier, onChange: (form: LoginForm?) -> Unit) {
    var email by remember {
        mutableStateOf("")
    }
    var emailError by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordError by remember {
        mutableStateOf("")
    }

    LaunchedEffect(emailError, passwordError) {
        if (emailError.isEmpty() && passwordError.isEmpty())
            onChange.invoke(LoginForm(email, password))
        else
            onChange.invoke(null)
    }

    Column(Modifier.then(modifier), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(
            email,
            onValueChange = {
                email = it
                emailError = email.isValidEmail() ?: ""
            },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            label = { Text(text = "Username") },
            leadingIcon = {
                Icon(Icons.Rounded.Email, contentDescription = "Username / Email")
            },
            isError = emailError.isNotEmpty(),
            supportingText = {
                // show error is email error contain text
                if (emailError.isNotEmpty()) {
                    Text(emailError)
                }
            }
        )
        TextField(
            password,
            onValueChange = {
                password = it
                passwordError = password.isValidPassword() ?: ""
            },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            label = { Text(text = "Password") },
            leadingIcon = {
                Icon(Icons.Rounded.Lock, contentDescription = "Password")
            },
            isError = passwordError.isNotEmpty(),
            supportingText = {
                if (passwordError.isNotEmpty()) {
                    Text(passwordError)
                }
            }
        )
    }
}


@Preview
@Composable
private fun LoginScreenPreview(modifier: Modifier = Modifier) {
    EduSignTheme(darkTheme = true) {
        Surface {
            LoginScreen(Modifier, rememberNavController())
        }
    }
}