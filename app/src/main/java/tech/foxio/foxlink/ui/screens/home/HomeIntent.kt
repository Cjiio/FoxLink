package tech.foxio.foxlink.ui.screens.home

sealed class HomeIntent {
    object SwitchConnect : HomeIntent()
    data class CheckServer(val url: String) : HomeIntent()
    data class ChangeServer(val url: String, val key: String) : HomeIntent()
}