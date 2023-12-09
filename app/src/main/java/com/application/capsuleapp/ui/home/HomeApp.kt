package com.application.capsuleapp.ui.home

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.application.capsuleapp.R
import com.application.capsuleapp.domain.model.SettingsPreference
import com.application.capsuleapp.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeApp(modifier: Modifier = Modifier, homeViewModel: HomeViewModel = hiltViewModel()) {

    val settingsUiState by homeViewModel.isListMode.collectAsState(initial = SettingsPreference(true))

    val categoryUIState by homeViewModel.categoryUIState.collectAsState()

    Scaffold(topBar = { HomeTopAppbar(
        settingsUiState = settingsUiState,
        onModeClick = homeViewModel::updateMode
    ) }) {
        HomeScreen(
            modifier = modifier.padding(it),
            settingsUiState = settingsUiState,
            categoryUIState = categoryUIState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppbar(modifier: Modifier = Modifier,
                          settingsUiState: SettingsPreference = SettingsPreference(false),
                          onModeClick: (Boolean) -> Unit = {}) {

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
                  IconButton(onClick = { onModeClick(settingsUiState.data.not()) }) {
                      Icon(imageVector = if (settingsUiState.data) Icons.Outlined.GridView else Icons.AutoMirrored.Outlined.List, contentDescription = null)
                  }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent))
}