package tech.foxio.foxlink.ui.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.launch
import tech.foxio.foxlink.R
import tech.foxio.foxlink.ui.components.OnboardingUI

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
//    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
//    val dataState by onboardingViewModel.dataState.collectAsState()
//    val onboardingData = dataState.onboardingData
    val onboardingData: List<OnboardingData> = listOf(
        OnboardingData(
            icon = R.drawable.ob1,
            title = "Safe and Secured",
            description = "We always been committed to protecting your privacy and your data."
        ),
        OnboardingData(
            icon = R.drawable.ob2,
            title = "Fast Servers",
            description = "We have best server around the world with super high speed connection."
        ),
        OnboardingData(
            icon = R.drawable.ob3,
            title = "3 Days Free Trial",
            description = "Only \$9.99 billed monthly after free trial. Recurring billing, cancel anytime."
        )
    )
    val onboardingState = rememberPagerState(initialPage = 0)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = onboardingState,
            pageCount = onboardingData.size,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        ) {
            OnboardingUI(onboardingData[it])
        }
        Spacer(modifier = Modifier.height(30.dp))
        //指示器
        HorizontalPagerIndicator(
            activeColor = MaterialTheme.colorScheme.primary,
            pagerState = onboardingState,
            pageCount = onboardingData.size,
            indicatorWidth = 20.dp,
            indicatorHeight = 8.dp,
            spacing = 5.dp
        )
        Spacer(modifier = Modifier.height(70.dp))
        val animationScope = rememberCoroutineScope()
        Button(
            shape = MaterialTheme.shapes.large,
            onClick = {
                animationScope.launch {
                    if (onboardingState.currentPage < onboardingData.size) {
                        onboardingState.animateScrollToPage(onboardingState.currentPage + 1)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(60.dp)
                .padding(horizontal = 25.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Next",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = "Skip",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen()
}