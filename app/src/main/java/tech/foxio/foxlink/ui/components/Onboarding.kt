package tech.foxio.foxlink.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.foxio.foxlink.R
import tech.foxio.foxlink.ui.screens.onboarding.OnboardingData

@Composable
//onboardingData: OnboardingData
fun OnboardingUI(onboardingData: OnboardingData) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = Modifier
            .height(500.dp)
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
    ) {
        Box(
            modifier = Modifier
                .paint(
                    painter = painterResource(id = R.drawable.onboarding_background),
                    sizeToIntrinsics = true,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                HeadImage(onboardingData)
                DashedLine()
                OnboardingContent(onboardingData)
            }
        }
    }
}

@Composable
private fun HeadImage(onboardingData: OnboardingData) {
    Image(
        painter = painterResource(id = onboardingData.icon),
        contentDescription = null,
        modifier = Modifier
            .height(200.dp)
    )
}

@Composable
private fun OnboardingContent(onboardingData: OnboardingData) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            color = MaterialTheme.colorScheme.onPrimary,
            text = onboardingData.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            color = MaterialTheme.colorScheme.onPrimary,
            text = onboardingData.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun DashedLine() {
    Spacer(modifier = Modifier.height(50.dp))
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(
        Modifier
            .fillMaxWidth()
            .height(1.dp)) {
        drawLine(
            color = Color.Gray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
    Spacer(modifier = Modifier.height(40.dp))
}

@Preview
@Composable
fun OnboardingUIPreview() {
    OnboardingUI(
        onboardingData = OnboardingData(
            icon = R.drawable.ob1,
            title = "Safe and Secured",
            description = "We always been committed to protecting your privacy and your data."
        )
    )
}