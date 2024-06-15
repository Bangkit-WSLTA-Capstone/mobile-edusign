package com.nekkiichi.edusign.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@Composable
fun ProfilePicture(modifier: Modifier = Modifier, imageUrl: Any?) {
    var retry by remember {
        mutableIntStateOf(3)
    }
    val context = LocalContext.current
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "Profile Picture",
        imageLoader = context.imageLoader,
        loading = {
            Box {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        },
        error = {
            Box {
                Icon(
                    Icons.Rounded.ErrorOutline, contentDescription = "",
                    Modifier
                        .align(Alignment.Center)
                        .size(24.dp), tint = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier
            .border(
                width = 5.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .then(modifier)
    )
}


@Preview
@Composable
private fun ProfilePicturePreview() {
    EduSignTheme(darkTheme = false) {
        Surface {
            Column(Modifier, verticalArrangement = Arrangement.Center) {
                ProfilePicture(
                    imageUrl = "httpxs://picsum.photos/id/235/100/100",
                    modifier = Modifier.size(75.dp)
                )
            }
        }
    }
}