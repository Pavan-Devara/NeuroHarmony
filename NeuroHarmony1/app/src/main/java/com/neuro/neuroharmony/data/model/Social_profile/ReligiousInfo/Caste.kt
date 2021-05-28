package com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo


import com.google.gson.annotations.SerializedName

data class Caste(
    @SerializedName("cate")
    val cate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("religion")
    val religion: Int
)