package tech.foxio.foxlink.ui.screens.about

sealed class AboutIntent {
    object GetVersionName : AboutIntent()
}