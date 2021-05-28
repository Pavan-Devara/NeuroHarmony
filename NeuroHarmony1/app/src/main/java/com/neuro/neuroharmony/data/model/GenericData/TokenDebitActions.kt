package com.neuro.neuroharmony.data.model.GenericData

import com.google.gson.annotations.SerializedName
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.Data

data class TokenDebitActions(
    val cb_retest: Int,
    val desire_retest: Int,
    val express_request: Int,
    val neuro_retest: Int,
    val request_manual_match: Int,
    @SerializedName("unblock-user")
    val unblock: Int
    )
