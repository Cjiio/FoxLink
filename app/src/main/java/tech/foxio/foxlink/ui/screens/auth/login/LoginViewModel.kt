package tech.foxio.foxlink.ui.screens.auth.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
//    @ActivityContext private val activityContext: Context,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _dataState = MutableStateFlow(DataState())
    val dataState: StateFlow<DataState> = _dataState.asStateFlow()

    fun sendUIIntent(loginIntent: LoginIntent) {
        when (loginIntent) {
            is LoginIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {
    }
}