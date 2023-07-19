package tech.foxio.foxlink.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import tech.foxio.foxlink.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import tech.foxio.foxlink.ui.screens.auth.login.LoginScreen
import tech.foxio.foxlink.ui.screens.auth.register.RegisterScreen
import tech.foxio.foxlink.ui.screens.home.HomeScreen
import tech.foxio.foxlink.ui.screens.onboarding.OnboardingScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}