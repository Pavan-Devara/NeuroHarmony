package com.neuro.neuroharmony.data.model.Push_notifications

data class DataNotificationRequest(
    val device_token: String,
    val os_type: Int
)