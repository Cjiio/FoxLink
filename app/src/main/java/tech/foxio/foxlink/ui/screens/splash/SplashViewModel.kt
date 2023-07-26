package tech.foxio.foxlink.ui.screens.splash

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _dataState = MutableStateFlow(DataState())
    val dataState: StateFlow<DataState> = _dataState.asStateFlow()

    fun sendUIIntent(splashIntent: SplashIntent) {
        when (splashIntent) {
            is SplashIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {
    }
}