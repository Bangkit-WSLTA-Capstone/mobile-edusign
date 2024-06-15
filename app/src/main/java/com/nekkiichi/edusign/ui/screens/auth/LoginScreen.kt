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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.RootRoutes
import com.nekkiichi.edusign.data.remote.response.LoginResponse
import com.nekkiichi.edusign.ui.composable.FilledTextField
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.composable.SecondaryButton
import com.nekkiichi.edusign.ui.composable.TextButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.utils.Status
import com.nekkiichi.edusign.utils.isValidEmail
import com.nekkiichi.edusign.utils.isValidPassword
import com.nekkiichi.edusign.viewModel.AuthViewModel

internal data class LoginForm(val email: String, val password: String)

internal class LoginHandler {
    var login: (LoginForm) -> Unit = {}
}

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    val loginState = authViewModel.loginStatus
    val handler = remember {
        LoginHandler().apply {
            login = { form ->
                authViewModel.login(form.email, form.password)
            }
        }
    }



    LoginScreenContent(navController, loginState, handler)
}

@Composable
private fun LoginScreenContent(
    navController: NavController, loginState: Status<LoginResponse>?, handler: LoginHandler
) {
    var loading by remember {
        mutableStateOf(false)
    }
    var loginForm: LoginForm? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(loginState) {
        loading = loginState is Status.Loading
    }

    Scaffold { paddingScaffold ->
        Column(Modifier.padding(paddingScaffold)) {
            Column(
                Modifier
                    .defaultMinSize(minHeight = 200.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Welcome Back!",
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Let's Sign in with your account!",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            LoginForm(
                Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                onChange = { loginForm = it }
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
                    .widthIn(320.dp)
            ) {
                PrimaryButton(
                    onCLick = {
                        val form = loginForm ?: return@PrimaryButton
                        handler.login.invoke(form)
                    },
                    Modifier.fillMaxWidth(),
                    enabled = loginForm != null && !loading // enable button if not in loading state
                ) {
                    Text(text = "SIGN IN")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Rounded.Login, contentDescription = "")
                }
                HorizontalDivider(Modifier.padding(8.dp))
                SecondaryButton(onCLick = {
                    navController.navigate(RootRoutes.Register.route)
                }, Modifier.fillMaxWidth()) {
                    Text(text = "CREATE ACCOUNT")
                }
                TextButton(onCLick = {
                    navController.navigate(RootRoutes.Home.route) {
                        popUpTo(RootRoutes.Login.route) {
                            inclusive = true
                        }
                    }
                }, Modifier.fillMaxWidth()) {
                    Text(text = "START AS GUEST")
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
        if (email.isEmpty() && password.isEmpty()) {
            onChange.invoke(null)
        } else if (emailError.isEmpty() && passwordError.isEmpty()) {
            onChange.invoke(LoginForm(email, password))
        } else {
            onChange.invoke(null)
        }
    }

    Column(Modifier.then(modifier), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        FilledTextField(email, onValueChange = {
            email = it
            emailError = email.isValidEmail() ?: ""
        }, Modifier.fillMaxWidth(), singleLine = true, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
        ),

            placeholder = { Text(text = "Email") }, leadingIcon = {
                Icon(Icons.Rounded.Email, contentDescription = "Username / Email")
            }, isError = emailError.isNotEmpty(), supportingText = {
                // show error is email error contain text
                if (emailError.isNotEmpty()) {
                    Text(emailError)
                }
            })
        FilledTextField(password,
            onValueChange = {
                password = it
                passwordError = password.isValidPassword() ?: ""
            },
            Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text(text = "Password") },
            leadingIcon = {
                Icon(Icons.Rounded.Lock, contentDescription = "Password")
            },
            isError = passwordError.isNotEmpty(),
            supportingText = {
                if (passwordError.isNotEmpty()) {
                    Text(passwordError)
                }
            })
    }
}


@Preview
@Composable
private fun LoginScreenPreview(modifier: Modifier = Modifier) {
    EduSignTheme() {
        Surface {
            LoginScreenContent(navController = rememberNavController(), null, LoginHandler())
        }
    }
}