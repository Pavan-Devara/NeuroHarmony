package com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo


import com.google.gson.annotations.SerializedName


data class Choice(
    @SerializedName("deeply_religious")
    val deeplyReligious: List<DeeplyReligiou>,
    @SerializedName("worship_place_visit")
    val worshipPlaceVisit: List<WorshipPlaceVisit>
)