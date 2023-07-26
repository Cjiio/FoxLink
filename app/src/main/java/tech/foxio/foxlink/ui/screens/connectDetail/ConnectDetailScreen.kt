package tech.foxio.foxlink.ui.screens.connectDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import tech.foxio.foxlink.R
import tech.foxio.foxlink.ui.theme.AppTheme

@Composable
fun ConnectDetailScreen(
//    connectDetailViewModel: ConnectDetailViewModel = hiltViewModel()
) {
//    val dataState by connectDetailViewModel.dataState.collectAsStateWithLifecycle()
    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = { HeadContent() },
        content = {
            Content(it)
        }
    )
}

@Composable
private fun Content(paddingValues: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .size(100.dp)
        ) {
            Icon(
                modifier = Modifier
                    .padding(30.dp),
                painter = painterResource(id = R.drawable.connect_icon),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline,
            text = "This is a summary of information from using FoxLink while connected"
        )
        Spacer(modifier = Modifier.height(50.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(3) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.large,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .size(70.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(10.dp),
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Location",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "United States - Miami",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeadContent() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .padding(horizontal = 25.dp),
        title = {
            Text(
                text = "Location",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            FilledIconButton(
                modifier = Modifier
                    .padding(),
                onClick = {
                },
                shape = MaterialTheme.shapes.large,
            ) {
                Surface(
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

@Preview
@Composable
fun ConnectDetailScreenPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            ConnectDetailScreen()
        }
    }
}