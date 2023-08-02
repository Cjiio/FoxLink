package tech.foxio.foxlink.ui.screens.auth.register

sealed class RegisterIntent {
    data class Register(val email: String, val password: String, val name: String) :
        RegisterIntent()
}