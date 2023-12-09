package com.application.capsuleapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.capsuleapp.domain.common.Resource
import com.application.capsuleapp.domain.model.Category
import com.application.capsuleapp.domain.model.SettingsPreference
import com.application.capsuleapp.domain.usecase.CategoryUseCase
import com.application.capsuleapp.domain.usecase.SettingsPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: SettingsPreferenceUseCase,
    private val categoryUseCase: CategoryUseCase

    ): ViewModel() {

    val isListMode: Flow<SettingsPreference> = useCase.readFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = SettingsPreference(true)
    )

    private var _categoryUIState = MutableStateFlow<Resource<Category>>(Resource.Loading)
    val categoryUIState: StateFlow<Resource<Category>> get() = _categoryUIState

    private var _loading = MutableStateFlow<Boolean>(true)
    val loading: StateFlow<Boolean> get() = _loading

    fun updateMode(value: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        useCase.updatePreference(value)
    }

    private fun getCategory() = viewModelScope.launch {
        categoryUseCase().collectLatest {
            _categoryUIState.value = it
        }
    }

    init {
        viewModelScope.launch {
            delay(3_000L)
            _loading.value = false
        }
        getCategory()
    }
}