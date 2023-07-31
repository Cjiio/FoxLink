package tech.foxio.foxlink.ui.screens.feedback

data class FeedbackState(
    val feedback: String = "",
    val isFeedbackSent: Boolean = false,
    val isFeedbackSending: Boolean = false,
    val isFeedbackError: Boolean = false
)