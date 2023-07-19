package tech.foxio.foxlink.ui.screens.onboarding

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _dataState = MutableStateFlow(DataState())
    val dataState: StateFlow<DataState>
        get() = _dataState

    fun sendUIIntent(onboardingIntent: OnboardingIntent) {
        when (onboardingIntent) {
            is OnboardingIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {
    }
}
data class OnboardingData(
    val icon : Int,
    val title : String,
    val description : String
)