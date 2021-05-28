package com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo


import com.google.gson.annotations.SerializedName


data class DeeplyReligiou(
    @SerializedName("deeply_religious")
    val deeplyReligious: String,
    @SerializedName("id")
    val id: String
)