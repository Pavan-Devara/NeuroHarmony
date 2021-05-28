package com.neuro.neuroharmony.data.model

import com.google.gson.annotations.SerializedName

data class DataTokenDebitActions (
    @SerializedName("express_request")
    val express_request: Int,
    @SerializedName("neuro_retest")
    val neuro_retest: Int,
    @SerializedName("desire_retest")
    val desire_retest: Int,
    @SerializedName("cb_retest")
    val cb_retest: Int,
    @SerializedName("request_manual_match")
    val request_manual_match: Int,
    @SerializedName("unblock-user")
    val unblock_user: Int
)