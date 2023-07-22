package tech.foxio.foxlink.ui.screens.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    init {
//        NetbirdModule.startService()
    }

    private val _dataState = MutableStateFlow(DataState())
    val dataState: StateFlow<DataState>
        get() = _dataState

    fun sendUIIntent(homeIntent: HomeIntent) {
        when (homeIntent) {
            is HomeIntent.SwitchConnect -> switchConnect()
        }
    }

    private fun switchConnect() {
//        NetbirdModule.switchConnect(true)
    }
}