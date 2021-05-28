package com.neuro.neuroharmony.data.model

data class UploadPhotoResponse(
    val `data`: DataUploadPhoto,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)

