package tech.foxio.foxlink.ui.screens.home

import android.ConnectionListener
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.foxio.foxlink.tool.ServiceStateListener
import tech.foxio.foxlink.utils.NetbirdModule
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private var isConnecting: Boolean = false

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _connectInfo = MutableStateFlow(ConnectInfo())
    val connectInfo: StateFlow<ConnectInfo> = _connectInfo.asStateFlow()

    private val _timeState = MutableStateFlow(TimeState())
    val timeState: StateFlow<TimeState> = _timeState.asStateFlow()

    class MyServiceStateListener(
        uiState: MutableStateFlow<UIState>,
        timeState: MutableStateFlow<TimeState>
    ) : ServiceStateListener {
        val _uiState = uiState
        val _timeState = timeState

        override fun onStarted() {
        }

        override fun onStopped() {
        }

        override fun onError(msg: String?) {
        }

    }

    init {
        NetbirdModule.setConnectionListener(MyConnectionListener(_connectInfo,_uiState, _timeState))
        NetbirdModule.setServiceStateListener(MyServiceStateListener(_uiState, _timeState))
        NetbirdModule.startService()
    }

    fun sendUIIntent(homeIntent: HomeIntent) {
        when (homeIntent) {
            is HomeIntent.SwitchConnect -> switchConnect()
        }
    }

    private fun switchConnect() {
        when (isConnecting) {
            true -> {
                _uiState.update {
                    it.copy(connectState = ConnectState.DISCONNECTING)
                }
                NetbirdModule.switchConnect(false)
            }

            false -> {
                _uiState.update {
                    it.copy(connectState = ConnectState.CONNECTING)
                }
                NetbirdModule.switchConnect(true)
            }
        }
        isConnecting = !isConnecting
    }

    private fun launchIO(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }

    class MyConnectionListener(
        _connectInfo: MutableStateFlow<ConnectInfo>,
        _uiState: MutableStateFlow<UIState>,
        _timeState: MutableStateFlow<TimeState>
    ) : ConnectionListener {
        val LOG_TAG = "HomeViewModel"
        val connectInfo = _connectInfo
        val uiState = _uiState
        val timeState = _timeState

        private var startTime: Long = 0L
        private var elapsedTime: Long = 0L
        private var isRunning: Boolean = false

        private fun toggleTimer() {
            if (isRunning) {
                elapsedTime = System.currentTimeMillis() - startTime
                isRunning = false
            } else {
                startTime = System.currentTimeMillis()
                elapsedTime = 0L
                isRunning = true
                CoroutineScope(Dispatchers.IO).launch {
                    while (isRunning) {
                        updateElapsedTime()
                        delay(1000)
                    }
                }
            }
        }

        private fun updateElapsedTime() {
            elapsedTime = System.currentTimeMillis() - startTime
            val seconds = elapsedTime / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            timeState.update {
                it.copy(
                    elapsedHours = hours.toInt(),
                    elapsedMinutes = minutes.toInt(),
                    elapsedSeconds = seconds.toInt(),
                    formattedTime = String.format(
                        "%02d:%02d:%02d",
                        hours,
                        minutes % 60,
                        seconds % 60
                    )
                )
            }
        }

        override fun onConnected() {
            uiState.update {
                it.copy(connectState = ConnectState.CONNECTED)
            }
            if (!isRunning) {
                toggleTimer()
            }
        }

        override fun onDisconnected() {
            uiState.update {
                it.copy(connectState = ConnectState.DISCONNECTED)
            }
            if (isRunning) {
                toggleTimer()
            }
        }

        override fun onConnecting() {
            uiState.update {
                it.copy(connectState = ConnectState.CONNECTING)
            }
        }

        override fun onDisconnecting() {
            uiState.update {
                it.copy(connectState = ConnectState.DISCONNECTING)
            }
        }

        override fun onAddressChanged(deviceName: String, ipRes: String) {
            //从ip中匹配IP地址
            val regex = Regex("(\\d+\\.\\d+\\.\\d+\\.\\d+)")
            if (regex.containsMatchIn(ipRes)) {
                val matchResult = regex.find(ipRes)
                if (matchResult != null) {
                    val (ip) = matchResult.destructured
                    connectInfo.update {
                        it.copy(deviceName = deviceName, ip = ip)
                    }
                }
            }
        }

        override fun onPeersListChanged(size: Long) {
            Log.d(LOG_TAG, "STATE: ConnectionListener onPeersListChanged $size")
//            CoroutineScope(Dispatchers.IO).launch {
//                uiState.update {
//                    it.copy(peers = NetbirdModule.getPeers())
//                }
//            }
        }
    }
}