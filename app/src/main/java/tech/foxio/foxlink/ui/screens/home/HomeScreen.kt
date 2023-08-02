package tech.foxio.foxlink.ui.screens.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tech.foxio.foxlink.R
import tech.foxio.foxlink.ui.theme.AppTheme
import tech.foxio.foxlink.utils.NetbirdModule

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val dataState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val connectInfo by homeViewModel.connectInfo.collectAsStateWithLifecycle()
    val timeState by homeViewModel.timeState.collectAsStateWithLifecycle()
    val connectState = dataState.connectState
    val tipsContent: String
    val tipsIcon: ImageVector
    val connectButtonBgColor: Color
    val connectButtonColor: Color
    val connectButtonIcon: Int

    when (connectState) {
        ConnectState.CONNECTED -> {
            tipsContent = "Connected"
            tipsIcon = Icons.Default.CheckCircle
            connectButtonBgColor = MaterialTheme.colorScheme.onPrimaryContainer
            connectButtonColor = MaterialTheme.colorScheme.primary
            connectButtonIcon = R.drawable.connect_icon
        }

        ConnectState.DISCONNECTED -> {
            tipsContent = "Top up to connect"
            tipsIcon = Icons.Default.Notifications
            connectButtonBgColor = MaterialTheme.colorScheme.surface
            connectButtonColor = MaterialTheme.colorScheme.onSurface
            connectButtonIcon = R.drawable.connect_icon
        }

        ConnectState.CONNECTING -> {
            tipsContent = "Connecting"
            tipsIcon = Icons.Default.Send
            connectButtonBgColor = MaterialTheme.colorScheme.surface
            connectButtonColor = MaterialTheme.colorScheme.onSurface
            connectButtonIcon = R.drawable.connecting_icon
        }

        ConnectState.DISCONNECTING -> {
            tipsContent = "Disconnecting"
            tipsIcon = Icons.Default.Notifications
            connectButtonBgColor = MaterialTheme.colorScheme.surface
            connectButtonColor = MaterialTheme.colorScheme.onSurface
            connectButtonIcon = R.drawable.connecting_icon
        }
    }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colorScheme.background,
        drawerBackgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
        topBar = { HeadContent(scaffoldState, scope) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp)
                    .padding(it)
            ) {
                ConnectionTime(timeState.formattedTime)
                ConnectionInfo(connectInfo)
                UpDownSpeed()
                ConnectButton(
                    homeViewModel,
                    connectButtonBgColor,
                    connectButtonColor,
                    connectButtonIcon
                )
                TipsView(tipsContent, tipsIcon)
            }
        },
        drawerContent = {
            DrawerContent(scaffoldState, scope)
        }
    )
}

@Preview
@Composable
fun DrawerContentPreview() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            DrawerContent(scaffoldState, scope)
        }
    }
}

@Composable
fun DrawerContent(scaffoldState: ScaffoldState, scope: CoroutineScope) {
    BackHandler(enabled = scaffoldState.drawerState.isOpen) {
        scope.launch {
            scaffoldState.drawerState.close()
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
            .padding(top = 100.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp),
            tint = Color.White
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.height(40.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .defaultMinSize(minHeight = 50.dp)
                            .fillMaxWidth()
                            .clickable {

                            },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "My Account",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .defaultMinSize(minHeight = 50.dp)
                            .fillMaxWidth()
                            .clickable {

                            },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Share App",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .defaultMinSize(minHeight = 50.dp)
                            .fillMaxWidth()
                            .clickable {

                            },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Build,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Advanced",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .defaultMinSize(minHeight = 50.dp)
                            .fillMaxWidth()
                            .clickable {

                            },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Setting",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "©2023 FoxLink",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ConnectButton(
    homeViewModel: HomeViewModel,
    connectButtonBgColor: Color,
    connectButtonColor: Color,
    connectButtonIcon: Int
) {
    val context = LocalContext.current as Activity
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 140.dp),
    ) {
        Surface(
            color = connectButtonBgColor,
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.size(180.dp)
        ) {
            Surface(
                onClick = {
//                    netbirdModule.switchConnect(true)
                    if (NetbirdModule.hasVpnPermission(context)) {
                        homeViewModel.sendUIIntent(HomeIntent.SwitchConnect)
                    }
                },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.padding(30.dp),
            ) {
                Surface(
                    color = connectButtonColor, shape = MaterialTheme.shapes.small
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onPrimary,
                        painter = painterResource(id = connectButtonIcon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(30.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ConnectButtonPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
//            ConnectButton()
        }
    }
}

@Composable
fun TipsView(tipsContent: String, tipsIcon: ImageVector) {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .padding(top = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = tipsIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = tipsContent,
            color = MaterialTheme.colorScheme.outline,
        )
    }
}

@Preview
@Composable
fun TipsViewPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
//            TipsView(connectState)
        }
    }
}

@Composable
fun SpeedView(
    icon: Int, text: String, speed: String
) {
    Row(
        modifier = Modifier.padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$speed kB/s",
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun ConnectionInfo(connectInfo: ConnectInfo) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.extraLarge,
        onClick = { /*TODO*/ },
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(70.dp)
            ) {
                Image(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = R.drawable.america_united_states),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = connectInfo.deviceName,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = connectInfo.ip,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Preview
@Composable
fun ConnectionInfoPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
//            ConnectionInfo()
        }
    }
}

@Composable
fun UpDownSpeed() {
    Row(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 30.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SpeedView(R.drawable.line_arrow_circle_down_light, "Download", "245")
        Divider(
            modifier = Modifier
                .height(40.dp)
                .width(1.dp),
        )
        SpeedView(R.drawable.line_arrow_circle_up_light, "Upload", "176")
    }
}

@Preview
@Composable
fun UpDownSpeedPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            UpDownSpeed()
        }
    }
}

@Composable
private fun ConnectionTime(connectTime: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Connecting Time",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = connectTime,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
fun ConnectionTimePreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
//            ConnectionTime()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeadContent(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .padding(horizontal = 25.dp),
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            FilledIconButton(
                modifier = Modifier
                    .padding(),
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
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
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        modifier = Modifier.padding(6.dp),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        },
        actions = {
            FilledIconButton(
                onClick = {
//                    scope.launch {
//                        scaffoldState.drawerState.open()
//                    }
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
                        painter = painterResource(id = R.drawable.fill_crown_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(6.dp),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            HomeScreen()
        }
    }
}