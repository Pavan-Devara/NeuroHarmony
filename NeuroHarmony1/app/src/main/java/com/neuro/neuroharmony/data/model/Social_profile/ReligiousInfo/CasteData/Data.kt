package com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo.CasteData


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("caste")
    val caste: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("religion")
    val religion: Int
)