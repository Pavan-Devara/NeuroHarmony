package com.neuro.neuroharmony.data.model.CompleteMatch


import com.google.gson.annotations.SerializedName

data class DataWithinOrOutsideNeuroHarmonyRequest(
    @SerializedName("do_not_match")
    val doNotMatch: Boolean,
    @SerializedName("final_match")
    val finalMatch: Boolean,
    @SerializedName("out_side_partner")
    val outSidePartner: Boolean
)