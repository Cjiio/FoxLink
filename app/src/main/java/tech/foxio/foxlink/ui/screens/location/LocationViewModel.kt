package tech.foxio.foxlink.ui.screens.location

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
//    @ActivityContext private val activityContext: Context,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _dataState = MutableStateFlow(DataState())
    val dataState: StateFlow<DataState>
        get() = _dataState

    fun sendUIIntent(locationIntent: LocationIntent) {
        when (locationIntent) {
            is LocationIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {
    }
}