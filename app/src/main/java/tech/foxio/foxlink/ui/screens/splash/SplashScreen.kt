package tech.foxio.foxlink.ui.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.foxio.foxlink.R
import tech.foxio.foxlink.ui.components.Full_Logo
import tech.foxio.foxlink.ui.theme.AppTheme

@Composable
fun SplashScreen(
//    splashViewModel: SplashViewModel = hiltViewModel()
) {
//    val dataState by splashViewModel.dataState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .paint(
                painter = painterResource(id = R.drawable.screen_background),
                sizeToIntrinsics = true,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Full_Logo(200.dp)
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            SplashScreen()
        }
    }
}