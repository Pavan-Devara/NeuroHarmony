package com.neuro.neuroharmony.data.model.Push_notifications

data class NotificationResponse(
    val `data`: DataNotificationResponse,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)