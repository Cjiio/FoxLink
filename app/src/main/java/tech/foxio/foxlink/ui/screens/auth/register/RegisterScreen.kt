package tech.foxio.foxlink.ui.screens.auth.register

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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.foxio.foxlink.R
import tech.foxio.foxlink.ui.screens.connectDetail.ConnectDetailScreen
import tech.foxio.foxlink.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
//    registerViewModel: RegisterViewModel = hiltViewModel()
) {
//    val dataState by registerViewModel.dataState.collectAsState()
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Column(
        modifier = Modifier
            .paint(
                painter = painterResource(id = R.drawable.screen_background),
                sizeToIntrinsics = true,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            .fillMaxSize()
            .padding(horizontal = 25.dp)
            .padding(top = 50.dp),
    ) {
        HeadContent()
        PortraitContent()
        RegisterForm()
    }
}

@Composable
private fun RegisterForm() {
    Spacer(modifier = Modifier.height(20.dp))
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            label = {
                Text(
                    text = "Full Name",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            label = {
                Text(
                    text = "Email Address",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            label = {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            enabled = true,
            shape = MaterialTheme.shapes.large,
            onClick = {
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Next",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Have an Account? Sign In",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun PortraitContent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
        )
        Text(
            text = "Tap to select new avatar",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
private fun HeadContent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.onBackground,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.return_icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp)
    ) {
        Text(
            text = "Create",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
    Spacer(modifier = Modifier.height(30.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Create account and enjoy 7 day free trial",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            RegisterScreen()
        }
    }
}