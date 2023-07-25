package tech.foxio.foxlink.ui.screens.home

import android.PeerInfoArray

data class UIState(
    val connectState : ConnectState = ConnectState.DISCONNECTED,
    val peers : PeerInfoArray = PeerInfoArray(),
)
data class ConnectInfo(
    val downloadSpeeds : Int = 0,
    val uploadSpeeds : Int = 0,
    val connectTime: String = "00:00:00",
    val deviceName : String = "No Connection",
    val ip : String = "0.0.0.0",
)
data class TimeState(
    val elapsedHours : Int = 0,
    val elapsedMinutes : Int = 0,
    val elapsedSeconds : Int = 0,
    val formattedTime: String = "00:00:00"
)
enum class ConnectState {
    CONNECTED,
    DISCONNECTED,
    CONNECTING,
    DISCONNECTING
}