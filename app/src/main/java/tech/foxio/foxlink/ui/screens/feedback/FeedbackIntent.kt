package tech.foxio.foxlink.ui.screens.feedback

sealed class FeedbackIntent {
    data class SendFeedback(val feedback: String) : FeedbackIntent()
}