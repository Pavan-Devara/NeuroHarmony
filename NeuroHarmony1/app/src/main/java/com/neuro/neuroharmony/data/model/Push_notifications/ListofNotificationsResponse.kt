package com.neuro.neuroharmony.data.model.Push_notifications

data class ListofNotificationsResponse(
    val `data`: List<DataListofNotificationsResponse>,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)