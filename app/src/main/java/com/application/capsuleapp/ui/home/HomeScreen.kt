package com.application.capsuleapp.ui.home

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.application.capsuleapp.domain.common.Resource
import com.application.capsuleapp.domain.model.Category
import com.application.capsuleapp.domain.model.SettingsPreference
import com.application.capsuleapp.ui.detail.DetailActivity
import com.application.capsuleapp.ui.theme.CapsuleAppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               settingsUiState: SettingsPreference = SettingsPreference(true),
               categoryUIState: Resource<Category> = Resource.Loading) {

    when(categoryUIState) {
        is Resource.Loading -> {
            CircularProgressIndicator(modifier = modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center),
                strokeWidth = 2.dp
                )
        }
        is Resource.Failure -> {
            Text(text = "Failure : ${categoryUIState.throwable}",
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center))
        }
        is Resource.Success -> {
            Column(modifier = modifier
                .fillMaxSize()
                .padding(16.dp)) {

                if (settingsUiState.data) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(categoryUIState.data.category ?: emptyList()) {
                            ItemCard(item = it)
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(categoryUIState.data.category ?: emptyList()) {
                            ItemCard(item = it)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemCard(modifier: Modifier = Modifier, item: String = "") {

    val context = LocalContext.current

    Button(onClick = { DetailActivity.startActivity(context as Activity, item) }, modifier = modifier.fillMaxWidth()) {
        Text(text = item, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenLightThemePreview() {
    CapsuleAppTheme(darkTheme = false) {
        HomeScreen()
    }
}


@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenDarkThemePreview() {
    CapsuleAppTheme(darkTheme = true) {
        HomeScreen()
    }
}