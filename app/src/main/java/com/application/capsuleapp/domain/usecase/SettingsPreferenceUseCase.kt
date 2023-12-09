package com.application.capsuleapp.domain.usecase

import com.application.capsuleapp.data.repository.SettingsPreferenceRepository
import com.application.capsuleapp.domain.model.SettingsPreference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SettingsPreferenceUseCase {
    val readFlow: Flow<SettingsPreference>
    suspend fun updatePreference(value: Boolean)
}

class GetSettingsPreferenceInteractor @Inject constructor(private val repository: SettingsPreferenceRepository): SettingsPreferenceUseCase {
    override val readFlow: Flow<SettingsPreference>
        get() = repository.readPreference

    override suspend fun updatePreference(value: Boolean) = repository.updatePreference(value)

}