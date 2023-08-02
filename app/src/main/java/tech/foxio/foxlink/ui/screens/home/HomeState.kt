package tech.foxio.foxlink.ui.screens.home

import android.PeerInfoArray

data class UIState(
    val connectState : ConnectState = ConnectState.DISCONNECTED,
    val peers : PeerInfoArray = PeerInfoArray(),
)
data class ConnectInfo(
    val downloadSpeeds: String = "0.00 kb/s",
    val uploadSpeeds: String = "0.00 kb/s",
    val connectTime: String = "00:00:00",
    val deviceName: String = "No Connection",
    val ip: String = "0.0.0.0",
)
enum class ConnectState {
    CONNECTED,
    DISCONNECTED,
    CONNECTING,
    DISCONNECTING
}