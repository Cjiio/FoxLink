package tech.foxio.foxlink.ui.screens.auth.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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

@Composable
fun LoginScreen(
//    loginViewModel: LoginViewModel = hiltViewModel()
) {
//    val dataState by loginViewModel.dataState.collectAsState()
    Column(
        modifier = Modifier
            .paint(
                painter = painterResource(id = R.drawable.screen_background),
                sizeToIntrinsics = true,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 50.dp)
    ) {
        HeadContent()
        Spacer(modifier = Modifier.height(20.dp))
        LoginForm()
    }
}

@Composable
private fun LoginForm() {
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 20.dp),
                text = "Forgot Password?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
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
                text = "Sgin In",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .height(50.dp)
        ) {
            Surface(
                border = BorderStroke(0.2.dp, MaterialTheme.colorScheme.secondary),
                color = MaterialTheme.colorScheme.onBackground,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(15.dp),
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Surface(
                border = BorderStroke(0.2.dp, MaterialTheme.colorScheme.secondary),
                color = MaterialTheme.colorScheme.onBackground,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(15.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "By continuing you argree Beetroot’s",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = "Terms of services & Privacy Policy",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
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
            text = "Welcome Back",
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
            text = "Login to continue",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}