package com.neuro.neuroharmony.data.model

import com.google.gson.annotations.SerializedName

data class DataGoogleFbSignInResponse(
    val account_status: Int,
    val baseline: Baseline,
    val user_type: Any,
    val email: String,
    val first_name: String,
    val last_name: String,
    val middle_name: String,
    val mobile_number: String,
    val profile_pic: String,
    val user_key: Int,
    val username: String,
    val social_login_status:Int
)