package tech.foxio.foxlink.ui.screens.location

sealed class LocationIntent {
    object LoadData : LocationIntent()
}