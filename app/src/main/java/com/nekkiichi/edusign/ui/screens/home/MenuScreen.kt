package com.nekkiichi.edusign.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.Constant.defaultGap
import com.nekkiichi.edusign.Constant.defaultNum
import com.nekkiichi.edusign.Constant.defaultShapeByPercent
import com.nekkiichi.edusign.R
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.viewModel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MenuScreen(navController: NavController, authViewModel: AuthViewModel) {
    val loginState = authViewModel.loginStatus
    Scaffold {
        Surface(
            Modifier
                .fillMaxSize()
                .padding(it)) {
            Text(text = "Status Login ${loginState.toString()}")
            MenuContent()
        }
    }
}

@Composable
fun MenuContent() {
    Column() {
//              Hero Section
        Surface(tonalElevation = 5.dp) {
            MenuHero(modifier = Modifier.padding(defaultNum.dp))
        }
//              Content Section
        Column(modifier = Modifier.padding(defaultNum.dp)) {
            PrimaryButton(
                onCLick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            ) {
                Text(text = "Edit Your Profile")
            }
            Spacer(modifier = Modifier.height((defaultNum / 2).dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height((defaultNum / 2).dp))
            PrimaryButton(
                onCLick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Logout")
            }
        }
    }
}

@Composable
fun MenuHero(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Menu and Settings",
            fontWeight = FontWeight.ExtraBold,
            fontSize = defaultNum.sp
        )
        Spacer(modifier = Modifier.height((defaultNum * 2).dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.example_avatar),
                contentDescription = "users avatar",
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .clip(defaultShapeByPercent)
                    .border(3.dp, MaterialTheme.colorScheme.primary, defaultShapeByPercent)
            )
            Spacer(modifier = Modifier.width(defaultGap))
            Column {
                Text(
                    text = "User Name",
                    fontWeight = FontWeight.Bold,
                    fontSize = (defaultNum * 5 / 3).sp
                )
                Text(text = "user@email.com", fontWeight = FontWeight.Light)
            }
        }
        Spacer(modifier = Modifier.height((defaultNum).dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenuHero() {
    EduSignTheme {
        MenuHero()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMenuScreen() {
    EduSignTheme {
        MenuScreen(navController = rememberNavController(), authViewModel = hiltViewModel())
    }
}