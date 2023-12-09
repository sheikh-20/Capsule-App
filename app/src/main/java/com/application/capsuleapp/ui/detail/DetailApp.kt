package com.application.capsuleapp.ui.detail

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.application.capsuleapp.R
import com.application.capsuleapp.domain.model.SettingsPreference
import com.application.capsuleapp.ui.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailApp(modifier: Modifier = Modifier, detailViewModel: DetailViewModel = hiltViewModel()) {

    val context = LocalContext.current

    val quizUIState by detailViewModel.quiz.collectAsState()
    val notesUIState by detailViewModel.notes.collectAsState()
    val videoUIState by detailViewModel.video.collectAsState()

    val detailUIState by detailViewModel.detailUIState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        detailViewModel.getQuestion((context as Activity).intent.getStringExtra(DetailActivity.TITLE) ?: "")
        detailViewModel.getNotes((context as Activity).intent.getStringExtra(DetailActivity.TITLE) ?: "")
        detailViewModel.getVideo((context as Activity).intent.getStringExtra(DetailActivity.TITLE) ?: "")
    }

    Scaffold(
        topBar = { DetailTopAppbar(title = (context as Activity).intent.getStringExtra(DetailActivity.TITLE) ?: "") },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        DetailScreen(
            modifier = modifier,
            paddingValues = it,
            item = (context as Activity).intent.getStringExtra(DetailActivity.TITLE) ?: "",
            quizUIState = quizUIState,
            detailUIState = detailUIState,
            notesUIState = notesUIState,
            videoUIState = videoUIState,
            checkCorrectAnswer = detailViewModel::checkCorrectAnswer,
            showNextQuiz = detailViewModel::showNextQuiz,
            snackbarHostState = snackbarHostState,
            onDismiss = detailViewModel::onDismiss,
            onPlayAgain = detailViewModel::onReset)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopAppbar(modifier: Modifier = Modifier, title: String = "") {

    TopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent))
}