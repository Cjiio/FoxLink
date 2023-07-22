package tech.foxio.foxlink.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tech.foxio.foxlink.R
import tech.foxio.foxlink.ui.theme.AppTheme

@Composable
fun HomeScreen(
//    homeViewModel: HomeViewModel = hiltViewModel()
) {
//    val dataState by homeViewModel.dataState.collectAsState()
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
                ConnectionTime()
                ConnectionInfo()
                UpDownSpeed()
                ConnectButton()
                TipsView()
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
            .padding(horizontal = 25.dp)
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
            verticalArrangement = Arrangement.spacedBy(40.dp),
            content = {
                items(5) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "My Account",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun ConnectButton() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 140.dp),
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.size(180.dp)
        ) {
            Surface(
                onClick = {
//                    netbirdModule.switchConnect(true)
//                    if (NetbirdModule.hasVpnPermission(context)){
//                        homeViewModel.sendUIIntent(HomeIntent.SwitchConnect)
//                    }
                },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.padding(30.dp),
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.onSurface, shape = MaterialTheme.shapes.small
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onPrimary,
                        painter = painterResource(id = R.drawable.connecting_icon),
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
            ConnectButton()
        }
    }
}

@Composable
fun TipsView() {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .padding(top = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.line_hand_pointing_light),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Tap to Connect",
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
            TipsView()
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
private fun ConnectionInfo() {
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
                    text = "United States",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "IP 37.120.202.186",
                    style = MaterialTheme.typography.bodySmall,
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
            ConnectionInfo()
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
private fun ConnectionTime() {
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
            text = "00:30:27",
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
            ConnectionTime()
        }
    }
}

@Composable
private fun HeadContent(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .padding(top = 50.dp)
            .fillMaxWidth()
    ) {
        FilledIconButton(
            onClick = { /*TODO*/ },
            shape = MaterialTheme.shapes.large,
        ) {
            Surface(
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
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
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        FilledIconButton(
            onClick = { /*TODO*/ },
            shape = MaterialTheme.shapes.large,
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.fill_crown_icon),
                    contentDescription = null,
                    modifier = Modifier.padding(6.dp),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
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