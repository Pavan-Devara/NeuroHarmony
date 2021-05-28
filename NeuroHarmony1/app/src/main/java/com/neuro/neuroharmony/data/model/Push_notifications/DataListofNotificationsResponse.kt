package com.neuro.neuroharmony.data.model.Push_notifications

data class DataListofNotificationsResponse(
    val created_date: String,
    val id: String,
    val message: String,
    val image_url: String,
    val read_status: Boolean,
    val title: String,
    val type: String
)