package com.neuro.neuroharmony.data.model.PaymentPackages


import com.google.gson.annotations.SerializedName

data class DataGetPaymentPackageDetails(
    @SerializedName("actual_price")
    val actualPrice: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("gst")
    val gst: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("is_eeg")
    val isEeg: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("neuro_tokens")
    val neuroTokens: Int,
    @SerializedName("offer_price")
    val offerPrice: Double,
    @SerializedName("order")
    val order: Int,
    @SerializedName("plan_type")
    val planType: String,
    @SerializedName("rack_price")
    val rackPrice: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("discount_amount")
    val DiscountPrice: String
)