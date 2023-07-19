package tech.foxio.foxlink.ui.screens.home

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.foxio.foxlink.MyApp
import tech.foxio.foxlink.ui.screens.home.DataState
import tech.foxio.foxlink.ui.screens.home.HomeIntent
import tech.foxio.netbirdlib.NetbirdModule
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _dataState = MutableStateFlow(DataState())
    val dataState: StateFlow<DataState>
        get() = _dataState

    fun sendUIIntent(homeIntent: HomeIntent) {
        when (homeIntent) {
            is HomeIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {
    }
}