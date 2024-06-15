package com.nekkiichi.edusign.ui.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShutterButton(modifier: Modifier = Modifier, onClick: () -> Unit, isPlayed: Boolean = false) {

    Button(
        onClick = onClick,
        Modifier
            .size(72.dp)
            .then(modifier),
        shape = CircleShape,
        colors = if (isPlayed) ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onError,
            containerColor = MaterialTheme.colorScheme.error
        ) else ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
//        if (isPlayed) {
//            Icon(Icons.Filled.Stop, contentDescription = "Shutter", Modifier.size(32.dp))
//        } else {
//            Icon(
//                Icons.Filled.FiberManualRecord,
//                contentDescription = "Shutter",
//                Modifier.size(32.dp)
//            )
//        }

        AnimatedContent(targetState = isPlayed, transitionSpec = {
            if (targetState) {
                slideInVertically { fullHeight -> fullHeight } + fadeIn() togetherWith slideOutVertically { fullHeight -> -fullHeight } + fadeOut()
            } else {
                slideInVertically { fullHeight -> -fullHeight } + fadeIn() togetherWith slideOutVertically { fullHeight -> fullHeight } + fadeOut()


            }.using(SizeTransform(clip = false))
        }, label = "") {
            if (it) {
                Icon(Icons.Filled.Stop, contentDescription = "Shutter", Modifier.size(32.dp))
            } else {
                Icon(
                    Icons.Filled.FiberManualRecord,
                    contentDescription = "Shutter",
                    Modifier.size(32.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun ShutterButtonPreview(dark: Boolean = false) {
    EduSignTheme() {
        Surface {
            Column {
                ShutterButton(onClick = { /*TODO*/ })
                ShutterButton(onClick = { /*TODO*/ }, isPlayed = true)
            }
        }
    }
}


@Preview
@Composable
private fun ShutterButtonLivePreview() {
    var isPlay by remember {
        mutableStateOf(false)
    }
    EduSignTheme {
        Surface {
            ShutterButton(onClick = { isPlay = !isPlay }, isPlayed = isPlay)
        }
    }
}