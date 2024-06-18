package com.nekkiichi.edusign.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.utils.Status
import com.nekkiichi.edusign.viewModel.SignWordViewModel

val words = listOf(
    "A",
    "B",
    "C",
    "D",
    "E",
    "F",
    "G",
    "H",
    "I",
    "J",
    "K",
    "L",
    "M",
    "N",
    "O",
    "P",
    "Q",
    "R",
    "S",
    "T",
    "U",
    "V",
    "W",
    "X",
    "Y",
    "Z"
)

private class ScreenHandler {
    var back = {}
    var showWordDialog: (choosenWord: String) -> Unit = {}
    var clearWord = {}
}

@Composable
fun SignWordsScreen(navController: NavController) {
    val signWordViewModel: SignWordViewModel = hiltViewModel()
    val signWordState by signWordViewModel.currentSignWord.collectAsState()
    val handler = ScreenHandler().apply {
        back = {
            navController.navigateUp()
        }
        showWordDialog = {
            signWordViewModel.getWord(it)
        }
        clearWord = {
            signWordViewModel.clear()
        }
    }
    SignWordsContent(signWordState, handler)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignWordsContent(state: Status<String>?, handler: ScreenHandler) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Sign Word Dictionary") })
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            LazyVerticalGrid(columns = GridCells.Adaptive(64.dp)) {
                items(words) { word ->
                    Box(
                        Modifier
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                            .size(64.dp)
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                            .clickable {
                                handler.showWordDialog.invoke(word.uppercase())
                            },
                    ) {
                        Text(
                            text = word,
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.titleLarge,
//                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            if (state != null) {
                SignDialog(handler = handler, state = state)
            }
        }
    }
}

@Composable
private fun SignDialog(handler: ScreenHandler, state: Status<String>) {
    Dialog(onDismissRequest = { handler.clearWord.invoke() }) {
        Card(
            Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp), shape = RoundedCornerShape(8.dp)
        ) {
            Box(Modifier.fillMaxSize()) {
                when (state) {
                    is Status.Failed -> {
                        Column(
                            Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Failed to get sign word")
                            Text(text = state.errorMessage)
                        }
                    }

                    is Status.Success -> {
                        Column(Modifier.align(Alignment.Center)) {
                            AsyncImage(
                                model = state.value,
                                contentDescription = "Sign Image",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    else -> {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SignDialogPreview() {
    EduSignTheme {
        Surface {
            SignDialog(handler = ScreenHandler(), state = Status.Loading)
        }
    }
}

@Preview
@Composable
private fun SignWordsContentPreview() {
    val handler = ScreenHandler()
    EduSignTheme {
        SignWordsContent(state = Status.Success("hello"), handler = handler)
    }
}