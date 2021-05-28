package com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo


import com.google.gson.annotations.SerializedName


data class Religion(
    @SerializedName("id")
    val id: Int,
    @SerializedName("religion")
    val religion: String
)