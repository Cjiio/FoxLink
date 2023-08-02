package tech.foxio.foxlink.ui.screens.auth.register

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
class RegisterViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val client: Client
) : ViewModel() {

    private val _dataState = MutableStateFlow(DataState())
    val dataState: StateFlow<DataState> = _dataState.asStateFlow()

    fun sendUIIntent(registerIntent: RegisterIntent) {
        when (registerIntent) {
            is RegisterIntent.Register -> register(
                registerIntent.email,
                registerIntent.password,
                registerIntent.name
            )
        }
    }

    private fun register(email: String, password: String, name: String) {
        L.d("register", client.toString())
    }
}