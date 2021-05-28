package com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo


import com.google.gson.annotations.SerializedName


data class WorshipPlaceVisit(
    @SerializedName("id")
    val id: String,
    @SerializedName("worship_place_visit")
    val worshipPlaceVisit: String
)