package tech.foxio.foxlink.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import tech.foxio.foxlink.ui.screens.about.AboutScreen
import tech.foxio.foxlink.ui.screens.home.HomeScreen
import tech.foxio.foxlink.ui.screens.location.LocationScreen
import tech.foxio.foxlink.ui.screens.settings.SettingsScreen
import tech.foxio.foxlink.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AboutScreen()
                }
            }
        }
    }

}