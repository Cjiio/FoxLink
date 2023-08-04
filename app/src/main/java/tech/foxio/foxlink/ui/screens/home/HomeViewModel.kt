package tech.foxio.foxlink.ui.screens.home

import android.ConnectionListener
import android.ErrListener
import android.SSOListener
import android.annotation.SuppressLint
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safframework.log.L
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
import tech.foxio.foxlink.utils.NetSpeedUtils
import tech.foxio.foxlink.utils.NetbirdModuleUtils
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    notificationManager: NotificationManagerCompat,
    notificationCompatBuilder: NotificationCompat.Builder
) : ViewModel() {

    private var isConnected: Boolean = false

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _connectInfo = MutableStateFlow(ConnectInfo())
    val connectInfo: StateFlow<ConnectInfo> = _connectInfo.asStateFlow()

    class MyServiceStateListener(
        uiState: MutableStateFlow<UIState>, connectState: MutableStateFlow<ConnectInfo>
    ) : ServiceStateListener {
        val _uiState = uiState
        val _connectState = connectState

        override fun onStarted() {
        }

        override fun onStopped() {
        }

        override fun onError(msg: String?) {
        }

    }

    init {
        NetbirdModuleUtils.setConnectionListener(
            MyConnectionListener(
                _connectInfo, _uiState, notificationManager, notificationCompatBuilder
            )
        )
        NetbirdModuleUtils.setServiceStateListener(MyServiceStateListener(_uiState, _connectInfo))
//        NetbirdModule.CheckServer("https://app.pipi.ltd:33073", CheckServerSSOListener())
//        NetbirdModule.ChangeServer("https://app.pipi.ltd:33073", "FD1282B6-C28B-48B4-8497-A4864ED7A049", ChangeServerErrListener())
        NetbirdModuleUtils.startService()
    }

    fun sendUIIntent(homeIntent: HomeIntent) {
        when (homeIntent) {
            is HomeIntent.SwitchConnect -> {
                switchConnect()
            }

            is HomeIntent.CheckServer -> {
                checkServer(homeIntent.url)
            }

            is HomeIntent.ChangeServer -> {
                changeServer(homeIntent.url, homeIntent.key)
            }
        }
    }

    private fun checkServer(
        url: String, ssoListener: SSOListener = CheckServerSSOListener()
    ) {
        launchIO {
            NetbirdModuleUtils.CheckServer(url, ssoListener)
        }
    }

    class CheckServerSSOListener : SSOListener {
        override fun onError(e: Exception) {
            L.d("e")
        }

        override fun onSuccess(result: Boolean) {
            L.d(result.toString())
        }
    }

    private fun changeServer(
        url: String, key: String, errListener: ErrListener = ChangeServerErrListener()
    ) {
        launchIO {
            NetbirdModuleUtils.ChangeServer(url, key, errListener)
        }
    }

    class ChangeServerErrListener : ErrListener {
        private val TAG = "ChangeServerErrListener"
        override fun onError(e: Exception) {
            L.d(TAG, e.toString())
        }

        override fun onSuccess() {
            L.d(TAG, "onSuccess")
        }
    }

    private fun switchConnect() {
        if (_uiState.value.connectState == ConnectState.CONNECTING || _uiState.value.connectState == ConnectState.DISCONNECTING) {
            return
        }
        when (isConnected) {
            true -> {
                _uiState.update {
                    it.copy(connectState = ConnectState.DISCONNECTING)
                }
                NetbirdModuleUtils.switchConnect(false)
            }

            false -> {
                _uiState.update {
                    it.copy(connectState = ConnectState.CONNECTING)
                }
                NetbirdModuleUtils.switchConnect(true)
            }
        }
        isConnected = !isConnected
    }

    private fun launchIO(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }

    class MyConnectionListener(
        private val connectInfo: MutableStateFlow<ConnectInfo>,
        private val uiState: MutableStateFlow<UIState>,
        private val notificationManager: NotificationManagerCompat,
        notificationCompatBuilder: NotificationCompat.Builder
    ) : ConnectionListener {
        private val LOG_TAG = "HomeViewModel"

        private val speed = NetSpeedUtils()

        private var startTime: Long = 0L
        private var elapsedTime: Long = 0L
        private var isRunning: Boolean = false

        private val builder = notificationCompatBuilder


        private fun toggleTimer() {
            if (isRunning) {
                elapsedTime = System.currentTimeMillis() - startTime
                isRunning = false
                connectInfo.update {
                    it.copy(
                        downloadSpeeds = "0 KB/s", uploadSpeeds = "0 KB/s", connectTime = "00:00:00"
                    )
                }
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
            connectInfo.update {
                it.copy(
                    downloadSpeeds = convertSpeed(speed.getTotalDownloadSpeed()),
                    uploadSpeeds = convertSpeed(speed.getTotalUploadSpeed()),
                    connectTime = String.format(
                        "%02d:%02d:%02d", hours, minutes % 60, seconds % 60
                    )
                )
            }
        }

        //转换网络速度 传入单位为Kb的float 返回单位为KB或MB的String
        private fun convertSpeed(speed: Float): String {
            return when {
                speed < 1024 -> {
                    String.format("%.2f KB/s", speed)
                }

                else -> {
                    String.format("%.2f MB/s", speed / 1024 / 1024)
                }
            }
        }

        @SuppressLint("MissingPermission")
        override fun onConnected() {
            CoroutineScope(Dispatchers.IO).launch {
                builder.setContentTitle("ConnectState")
                builder.setContentText("Connected")
                notificationManager.notify(1, builder.build())
                uiState.update {
                    it.copy(connectState = ConnectState.CONNECTED)
                }
                if (!isRunning) {
                    toggleTimer()
                }
            }
        }

        @SuppressLint("MissingPermission")
        override fun onDisconnected() {
            CoroutineScope(Dispatchers.IO).launch {
                builder.setContentTitle("ConnectState")
                builder.setContentText("Disconnected")
                notificationManager.notify(1, builder.build())
                notificationManager.cancel(1)
                uiState.update {
                    it.copy(connectState = ConnectState.DISCONNECTED)
                }
                if (isRunning) {
                    toggleTimer()
                }
            }
        }

        @SuppressLint("MissingPermission")
        override fun onConnecting() {
            CoroutineScope(Dispatchers.IO).launch {
                builder.setContentTitle("ConnectState")
                builder.setContentText("Connecting")
                notificationManager.notify(1, builder.build())
                uiState.update {
                    it.copy(connectState = ConnectState.CONNECTING)
                }
            }
        }

        @SuppressLint("MissingPermission")
        override fun onDisconnecting() {
            CoroutineScope(Dispatchers.IO).launch {
                builder.setContentTitle("ConnectState")
                builder.setContentText("Disconnecting")
                notificationManager.notify(1, builder.build())
                uiState.update {
                    it.copy(connectState = ConnectState.DISCONNECTING)
                }
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
            L.d(LOG_TAG, "STATE: ConnectionListener onPeersListChanged $size")
//            CoroutineScope(Dispatchers.IO).launch {
//                uiState.update {
//                    it.copy(peers = NetbirdModule.getPeers())
//                }
//            }
        }
    }
}