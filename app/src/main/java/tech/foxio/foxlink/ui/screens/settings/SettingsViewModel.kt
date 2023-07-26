package tech.foxio.foxlink.ui.screens.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import tech.foxio.foxlink.ui.screens.splash.DataState
import tech.foxio.foxlink.ui.screens.splash.SplashIntent
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()
    val appTheme = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.DARK_MODE_ENABLED] ?: false
        }
    suspend fun toggleDarkMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE_ENABLED] = enabled
        }
    }

    fun sendUIIntent(splashIntent: SplashIntent) {
        when (splashIntent) {
            is SplashIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {

    }
}

object PreferencesKeys {
    val DARK_MODE_ENABLED = booleanPreferencesKey("dark_mode_enabled")
}
