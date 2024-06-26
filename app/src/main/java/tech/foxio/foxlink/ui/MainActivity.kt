package tech.foxio.foxlink.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import tech.foxio.foxlink.ui.screens.home.HomeScreen
import tech.foxio.foxlink.ui.theme.AppTheme
import tech.foxio.foxlink.utils.NetbirdModuleUtils

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetbirdModuleUtils.Init(this)
        setContent {
            AppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    HomeScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NetbirdModuleUtils.Destroy()
    }
}