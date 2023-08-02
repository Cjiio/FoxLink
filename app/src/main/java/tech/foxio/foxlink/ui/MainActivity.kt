package tech.foxio.foxlink.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import tech.foxio.foxlink.ui.screens.splash.SplashScreen
import tech.foxio.foxlink.ui.theme.AppTheme
import tech.foxio.foxlink.utils.NetbirdModule

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetbirdModule.Init(this)
        setContent {
            AppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    SplashScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NetbirdModule.Destroy()
    }
}