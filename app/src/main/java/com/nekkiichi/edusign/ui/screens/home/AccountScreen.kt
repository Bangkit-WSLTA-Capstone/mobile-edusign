package com.nekkiichi.edusign.ui.screens.home

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.R
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@Preview(showBackground = true)
@Composable
fun AccountHero(){
    Image(painter = painterResource(id = R.drawable.example_avatar), contentDescription = "users avatar", modifier = Modifier.width(64.dp).height(64.dp))
}
@Composable
fun AccountScreen() {
    Scaffold {
        Surface(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .padding(it)
                    .padding(15.dp)) {
                PrimaryButton(onCLick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                    Text(text= "Edit Your Profile")
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun PreviewAccountScreen() {
    EduSignTheme {
        AccountScreen()
    }
}