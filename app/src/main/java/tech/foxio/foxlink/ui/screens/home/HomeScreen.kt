package tech.foxio.foxlink.ui.screens.home

import android.app.Activity
import android.net.VpnService
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tech.foxio.foxlink.R
import tech.foxio.netbirdlib.NetbirdModule

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val dataState by homeViewModel.dataState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 50.dp)
    ) {
        HeadContent()
        ConnectionTime()
        ConnectionInfo()
        UpDownSpeed()
        ConnectButton()
        Tip()
    }
}

@Composable
fun ConnectButton() {
    val context = LocalContext.current
    Log.d("XXXXXXXXXXXXXXXX",context.toString())
    val netbirdModule = NetbirdModule(context)
    netbirdModule.startService()
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
            modifier = Modifier
                .size(180.dp)
        ) {
            Surface(
                onClick = {
                    netbirdModule.switchConnect(true)
                },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .padding(30.dp),
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onPrimary,
                        painter = painterResource(id = R.drawable.power_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(40.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Tip() {
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

@Composable
fun SpeedView(
    icon: Int,
    text: String,
    speed: String
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp),
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
                modifier = Modifier
                    .size(70.dp)
            ) {
                Image(
                    modifier = Modifier
                        .padding(10.dp),
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

@Composable
fun UpDownSpeed() {
    Row(
        modifier = Modifier
            .padding(vertical = 15.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SpeedView(R.drawable.line_arrow_circle_down_light, "Download", "245")
        Spacer(modifier = Modifier.width(40.dp))
        Surface(
            modifier = Modifier
                .height(40.dp)
                .width(1.dp),
            color = MaterialTheme.colorScheme.outline,
            shape = MaterialTheme.shapes.small,
            content = {}
        )
        Spacer(modifier = Modifier.width(40.dp))
        SpeedView(R.drawable.line_arrow_circle_up_light, "Upload", "176")
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
            text = "00:30:26",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun HeadContent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    ) {
        FilledIconButton(
            onClick = { /*TODO*/ },
            shape = MaterialTheme.shapes.small,
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {

                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.menu_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(6.dp),
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
            shape = MaterialTheme.shapes.small,
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
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}