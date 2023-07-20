package tech.foxio.foxlink.ui.screens.home

data class DataState(
    val data: String = "HomeScreen",
    val connectState : ConnectState = ConnectState.DISCONNECTED,
    val downloadSpeeds : Int = 0,
    val uploadSpeeds : Int = 0,
    val connectTime: String = "00:00:00"
)
enum class ConnectState {
    CONNECTED,
    DISCONNECTED,
    CONNECTING
}