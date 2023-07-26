package tech.foxio.foxlink.ui.screens.location

import androidx.compose.animation.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import tech.foxio.foxlink.R
import tech.foxio.foxlink.ui.screens.onboarding.OnboardingScreen
import tech.foxio.foxlink.ui.theme.AppTheme
import java.util.UUID

@Composable
fun LocationScreen(
//    locationViewModel: LocationViewModel = hiltViewModel()
) {
//    val dataState by locationViewModel.dataState.collectAsStateWithLifecycle()
//    val serverData = dataState.serverData
    val serverData: ArrayList<String> = arrayListOf(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
    )
    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = { HeadContent() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp)
                    .padding(it)
            ) {
                TabContent()
                Content(serverData)
            }
        }
    )
}

@Composable
private fun Content(serverData: ArrayList<String>) {
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Smart Location",
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    val selectedTag = remember { mutableStateOf("Null") }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize(),
        content = {
            items(serverData.size) {
                Surface(
                    border =
                    if (serverData[it] == selectedTag.value)
                        BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                    else
                        null,
                    color =
                    if (serverData[it] == selectedTag.value)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.large,
                    onClick = { selectedTag.value = serverData[it] },
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
                                .size(60.dp)
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp),
                                    tint = Color.Green
                                )
                                Text(
                                    text = "88 ms",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        RadioButton(
//                                selected = it == 0,
                            selected = serverData[it] == selectedTag.value,
                            onClick = {
                                selectedTag.value = serverData[it]
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun TabContent() {
    var selectedIndex by remember { mutableStateOf(0) }
    val list = listOf("Premium", "Free")
    Spacer(modifier = Modifier.height(30.dp))
    Surface(
        shape = MaterialTheme.shapes.large
    ) {
        TabRow(
            divider = {},
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .height(70.dp),
            selectedTabIndex = selectedIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    height = 0.dp,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedIndex])
                )
            },
        ) {
            list.forEachIndexed { index, text ->
                val selected = selectedIndex == index
                val textColor = remember {
                    Animatable(Color.White)
                }
                LaunchedEffect(
                    selected
                ) {
                    textColor.animateTo(if (selected) Color.White else Color.Gray)
                }
                Tab(
                    modifier = Modifier.zIndex(2f),
                    selected = selected,
                    onClick = { selectedIndex = index },
                    text = {
                        Text(
                            text = text,
                            color = textColor.value,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    interactionSource = NoRippleInteractionSource()
                )
            }
        }
    }
}

class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
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
        },
        actions = {
            FilledIconButton(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.large,
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
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
fun LocationScreenPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            LocationScreen()
        }
    }
}