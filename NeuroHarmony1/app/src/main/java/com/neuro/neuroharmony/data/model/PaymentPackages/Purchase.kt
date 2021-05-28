package com.neuro.neuroharmony.data.model.PaymentPackages

import java.io.Serializable

data class Purchase(
    val actual_price: Double,
    val created_date: String,
    val description: String,
    val discount: Int,
    val gst: Double,
    val id: String,
    val image: String,
    val is_eeg: Boolean,
    val name: String,
    val neuro_tokens: Int,
    val offer_price: Double,
    val plan_type: String,
    val rack_price: Double,
    val payment_status:String
): Serializable