package tech.foxio.foxlink.ui.screens.about

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.foxio.foxlink.BuildConfig
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        launchIO {
            _uiState.update {
                it.copy(
                    versionName = BuildConfig.VERSION_NAME,
                    versionCode = BuildConfig.VERSION_CODE
                )
            }
        }
    }

    fun sendUIIntent(aboutIntent: AboutIntent) {
        when (aboutIntent) {
            is AboutIntent.GetVersionName -> getVersionName()
        }
    }

    private fun getVersionName() {
    }

    private fun launchIO(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }
}