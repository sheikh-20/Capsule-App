package com.application.capsuleapp.ui.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.application.capsuleapp.R
import com.application.capsuleapp.domain.common.Resource
import com.application.capsuleapp.domain.model.Quiz
import com.application.capsuleapp.ui.theme.CapsuleAppTheme
import com.application.capsuleapp.ui.viewmodel.DetailUIState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.rizzi.bouquet.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "DetailScreen"
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun DetailScreen(modifier: Modifier = Modifier,
                 paddingValues: PaddingValues = PaddingValues(),
                 item: String = "",
                 quizUIState: Resource<List<Quiz>> = Resource.Loading,
                 notesUIState: Resource<String> = Resource.Loading,
                 videoUIState: Resource<String> = Resource.Loading,
                 detailUIState: DetailUIState = DetailUIState(),
                 checkCorrectAnswer: (Quiz, String, (String) -> Unit) -> Unit = { _, _, _ ->  },
                 showNextQuiz: (List<Quiz>, Quiz, String) -> Unit = { _, _, _ -> },
                 snackbarHostState: SnackbarHostState = SnackbarHostState(),
                 onDismiss: () -> Unit = { },
                 onPlayAgain: () -> Unit = { }
) {
    val items = listOf(
        "Quiz",
        "Notes",
        "Video",
    )

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pager = rememberPagerState()

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            start = 0.dp,
            end = 0.dp
        )) {

        TabRow(selectedTabIndex = pager.currentPage) {
            items.forEachIndexed { index, horizontalPagerContent ->
                Tab(selected = pager.currentPage == index,
                    onClick = { /*TODO*/ },
                    text = {
                        Text(text = items[index])
                    })
            }
        }

        HorizontalPager(count = items.size, state = pager, modifier = modifier.weight(1f)) { index ->
            when (index) {
                0 -> {
                    when (quizUIState) {
                        is Resource.Loading -> {
                            androidx.compose.material.CircularProgressIndicator(
                                modifier = modifier
                                    .fillMaxSize()
                                    .wrapContentSize(align = Alignment.Center),
                                strokeWidth = 2.dp
                            )
                        }
                        is Resource.Failure -> {
                            Text(text = "Failure : ${quizUIState.throwable}",
                                modifier = modifier
                                    .fillMaxSize()
                                    .wrapContentSize(align = Alignment.Center))
                        }
                        is Resource.Success -> {

                            var selectedOption by remember { mutableIntStateOf(0) }

                            Column(modifier = modifier
                                .fillMaxSize()
                                .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(30.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {

                                Text(text = "Question ${detailUIState.quizIndex.inc()}/${quizUIState.data.size}", style = MaterialTheme.typography.displaySmall)

                                LinearProgressIndicator(progress = (detailUIState.quizIndex.inc().toFloat() / quizUIState.data.size), modifier = Modifier.fillMaxWidth())

                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                                    Card(modifier = Modifier.border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = CardDefaults.shape)) {

                                        Text(
                                            text = quizUIState.data[detailUIState.quizIndex].question ?: "",
                                            modifier = Modifier.padding(16.dp),
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold)
                                        Timber.tag(TAG).d(quizUIState.data.toString())
                                    }

                                    quizUIState.data[detailUIState.quizIndex].options?.forEachIndexed { index, option ->
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            RadioButton(selected = index == selectedOption, onClick = { selectedOption = index })
                                            Text(text = option ?: "",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier.clickable(onClick = { selectedOption = index }))
                                        }
                                    }
                                }

                                Spacer(modifier = modifier.weight(1f))

                                Row(modifier = modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                                    OutlinedButton(
                                        onClick = { checkCorrectAnswer(
                                            quizUIState.data[detailUIState.quizIndex],
                                            quizUIState.data[detailUIState.quizIndex].options?.get(selectedOption) ?: ""
                                        ) {
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar(message = it, duration = SnackbarDuration.Short)
                                            }
                                        }
                                        },
                                        modifier = Modifier.weight(1f)) {
                                        Text(text = "Check answer")
                                    }

                                    Button(onClick = { showNextQuiz(
                                        quizUIState.data,
                                        quizUIState.data[detailUIState.quizIndex],
                                        quizUIState.data[detailUIState.quizIndex].options?.get(selectedOption) ?: ""
                                        )  },
                                        modifier = Modifier.weight(1f)) {
                                        Text(text = "Next question")
                                    }
                                }
                            }

                            if (detailUIState.showFinalScoreDialog) {
                                FinalScoreDialog(
                                    onDismiss = onDismiss,
                                    onPlayAgain = onPlayAgain,
                                    score = detailUIState.score
                                )
                            }
                        }
                    }
                }
                1 -> {
                    Column(modifier = modifier
                        .fillMaxSize()
                        .padding(32.dp)) {

                        when (notesUIState) {
                            is Resource.Loading -> {
                             CircularProgressIndicator(
                                    modifier = modifier
                                        .fillMaxSize()
                                        .wrapContentSize(align = Alignment.Center),
                                    strokeWidth = 2.dp
                                )
                            }
                            is Resource.Failure -> {
                                Text(text = "Failure : ${notesUIState.throwable}",
                                    modifier = modifier
                                        .fillMaxSize()
                                        .wrapContentSize(align = Alignment.Center))
                            }
                            is Resource.Success ->{
                                VerticalPDFReader(
                                    state =  rememberVerticalPdfReaderState(
                                        resource = ResourceType.Remote(notesUIState.data),
                                        isZoomEnable = true
                                    ),
                                    modifier = modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
                2 -> {
                    when(videoUIState) {
                        is Resource.Loading -> {
                            androidx.compose.material.CircularProgressIndicator(
                                modifier = modifier
                                    .fillMaxSize()
                                    .wrapContentSize(align = Alignment.Center),
                                strokeWidth = 2.dp
                            )
                        }
                        is Resource.Failure -> {
                            Text(text = "Failure : ${videoUIState.throwable}",
                                modifier = modifier
                                    .fillMaxSize()
                                    .wrapContentSize(align = Alignment.Center))
                        }
                        is Resource.Success -> {
                            YoutubeScreen(videoId = videoUIState.data)
                        }
                    }

                }
            }
        }
    }
}

@Composable
private fun FinalScoreDialog(modifier: Modifier = Modifier,
                                 score: Int = 0,
                                 onDismiss: () -> Unit = { },
                                 onPlayAgain: () -> Unit = { }) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Congratulations") },
        text = { Text(text = "Your scored: $score") },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Exit")
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = "Play again")
            }
        }
    )
}


@Composable
fun YoutubeScreen(videoId: String = "", modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    AndroidView(factory = {
        var view = YouTubePlayerView(it)
        val fragment = view.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        )
        view
    })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailScreenLightThemePreview() {
    CapsuleAppTheme(darkTheme = false) {
        DetailScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun DetailScreenDarkThemePreview() {
    CapsuleAppTheme(darkTheme = true) {
        DetailScreen()
    }
}