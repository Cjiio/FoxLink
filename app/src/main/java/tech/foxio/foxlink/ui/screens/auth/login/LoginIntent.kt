package tech.foxio.foxlink.ui.screens.auth.login

sealed class LoginIntent {
    data class Login(val email: String, val password: String) : LoginIntent()
}