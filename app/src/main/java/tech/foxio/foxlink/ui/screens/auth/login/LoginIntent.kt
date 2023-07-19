package tech.foxio.foxlink.ui.screens.auth.login

sealed class LoginIntent {
    object LoadData : LoginIntent()
}