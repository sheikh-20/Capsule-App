package com.application.capsuleapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.application.capsuleapp.domain.model.SettingsPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

interface SettingsPreferenceRepository {
    val readPreference: Flow<SettingsPreference>
    suspend fun updatePreference(value: Boolean)
}

class SettingsPreferenceImpl @Inject constructor(private val datastore: DataStore<Preferences>): SettingsPreferenceRepository {
    private object PreferenceKeys {
        val IS_LIST_MODE = booleanPreferencesKey("is_list_mode")
    }

    override val readPreference: Flow<SettingsPreference>
        get() = datastore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {  preference ->
                val isListMode = preference[PreferenceKeys.IS_LIST_MODE] ?: true
                SettingsPreference(isListMode)
            }

    override suspend fun updatePreference(value: Boolean) {
        datastore.edit { preference ->
            preference[PreferenceKeys.IS_LIST_MODE] = value
        }
    }
}