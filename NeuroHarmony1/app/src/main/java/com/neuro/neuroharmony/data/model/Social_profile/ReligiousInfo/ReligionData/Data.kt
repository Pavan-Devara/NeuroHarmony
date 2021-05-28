package com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo.ReligionData


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("religion")
    val religion: String
)