package tech.foxio.foxlink.ui.screens.onboarding

import tech.foxio.foxlink.R


data class DataState(
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
)