package tech.foxio.foxlink.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.foxio.foxlink.ui.theme.AppTheme

@Composable
fun SettingsScreen(
//    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val settingsValue = listOf(
        "Connection mode",
        "Anonymous statistics",
        "Connect when app starts",
        "Show notification",
        "Switch block",
        "Imrpove connection stability",
        "Privacy Policy",
        "About"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                SettingsScreenHeadContent()
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(horizontal = 25.dp)
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        content = {
                            item {
                                Text(
                                    text = "General Settings",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                )
                            }
                            items(settingsValue.size) {
                                SettingOption(
                                    label = settingsValue[it],
                                    value = true,
                                    onValueChange = {

                                    }
                                )
                            }
                        })
                }
            }
        )
    }
}

@Composable
fun SettingOption(label: String, value: Boolean, onValueChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
        )
        Switch(checked = value, onCheckedChange = onValueChange)
    }
}

@Preview(name = "SettingOption")
@Composable
private fun PreviewSettingOption() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            SettingOption(
                label = "Connection mode",
                value = true,
                onValueChange = {

                }
            )
        }
    }
}

@Preview(name = "SettingsScreen")
@Composable
private fun PreviewSettingsScreen() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            SettingsScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreenHeadContent() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .padding(horizontal = 25.dp),
//            .padding(top = 50.dp),
        title = {
            Text(
                text = "Settings",
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

@Preview(name = "SettingsScreenHeadContent")
@Composable
private fun PreviewSettingsScreenHeadContent() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            SettingsScreenHeadContent()
        }
    }
}