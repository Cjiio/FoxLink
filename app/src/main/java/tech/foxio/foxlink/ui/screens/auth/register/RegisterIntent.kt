package tech.foxio.foxlink.ui.screens.auth.register

sealed class RegisterIntent {
    object LoadData : RegisterIntent()
}