package com.neuro.neuroharmony.data.model.Push_notifications

data class DataNotificationResponse(
    val created_date: String,
    val device_token: String,
    val id: String,
    val modified_date: String,
    val os_type: Int,
    val user_key: Int
)