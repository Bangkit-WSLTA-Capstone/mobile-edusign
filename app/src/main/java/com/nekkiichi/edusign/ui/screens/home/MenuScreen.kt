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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.Constant.defaultGap
import com.nekkiichi.edusign.Constant.defaultNum
import com.nekkiichi.edusign.Constant.defaultShape
import com.nekkiichi.edusign.Constant.defaultShapeByPercent
import com.nekkiichi.edusign.R
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@Composable
fun MenuHero() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.example_avatar),
            contentDescription = "users avatar",
            modifier = Modifier
                .width(64.dp)
                .height(64.dp)
                .clip(defaultShapeByPercent)
                .border(2.dp, MaterialTheme.colorScheme.primary, defaultShapeByPercent)
        )
        Spacer(modifier = Modifier.width(defaultGap))
        Column {
            Text(
                text = "User Name",
                fontWeight = FontWeight.Bold,
                fontSize = (defaultNum * 5 / 3).sp
            )
            Text(text = "user@email.com")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenuHero() {
    EduSignTheme {
        MenuHero()
    }
}


@Composable
fun MenuScreen(navController: NavController) {
    Scaffold {
        Surface(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .padding(it)
                    .padding(15.dp)
            ) {
                MenuHero()
                Spacer(modifier = Modifier.height(15.dp))
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
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    EduSignTheme {
        MenuScreen(navController = rememberNavController())
    }
}