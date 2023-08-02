package tech.foxio.foxlink.ui.screens.auth.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import com.safframework.log.L
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.Client
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val client: Client
) : ViewModel() {

    private val _dataState = MutableStateFlow(DataState())
    val dataState: StateFlow<DataState> = _dataState.asStateFlow()

    fun sendUIIntent(loginIntent: LoginIntent) {
        when (loginIntent) {
            is LoginIntent.Login -> login(loginIntent.email, loginIntent.password)
        }
    }

    private fun login(email: String, password: String) {
        L.d("login", client.toString())
    }

    private fun loadData() {
    }
}