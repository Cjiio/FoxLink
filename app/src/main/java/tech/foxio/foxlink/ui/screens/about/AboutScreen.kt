package tech.foxio.foxlink.ui.screens.about

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.foxio.foxlink.R
import tech.foxio.foxlink.ui.components.Full_Logo
import tech.foxio.foxlink.ui.theme.AppTheme

@Composable
fun AboutScreen(
    aboutViewModel: AboutViewModel = hiltViewModel()
) {
    val uiState by aboutViewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .paint(
                painter = painterResource(id = R.drawable.screen_background),
                sizeToIntrinsics = true,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            .fillMaxSize()
    ) {
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                AboutScreenHeadContent()
            },
            content = {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Full_Logo(size = 200.dp)
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Version ${uiState.versionName}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = "Build ${uiState.versionCode}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 20.dp),
                        text = "©2023 FoxIO Tech",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            AboutScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutScreenHeadContent() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .padding(horizontal = 25.dp),
        title = {
            Text(
                text = "About",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            FilledIconButton(
                modifier = Modifier
                    .padding(),
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.large,
            ) {
                Surface(
                    onClick = {
                    },
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier.padding(6.dp),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    )
}

@Preview(name = "AboutScreenHeadContent")
@Composable
private fun PreviewAboutScreenHeadContent() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            AboutScreenHeadContent()
        }
    }
}