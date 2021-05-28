package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataUserTypeRequest(
    @SerializedName("user_type")
    val userType: Int,
    @SerializedName("referral_code")
    val referralCode: String

)