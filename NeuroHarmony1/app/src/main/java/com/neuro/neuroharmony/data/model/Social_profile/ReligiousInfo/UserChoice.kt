package com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo


import com.google.gson.annotations.SerializedName


data class UserChoice(
    @SerializedName("caste")
    val caste: Caste,
    @SerializedName("created_date")
    val createdDate: Any,
    @SerializedName("deeply_religious")
    val deeplyReligious: Any,
    @SerializedName("deeply_religious_text")
    val deeplyReligiousText: Any,
    @SerializedName("horoscope")
    val horoscope: Any,
    @SerializedName("id")
    val id: Any,
    @SerializedName("modified_date")
    val modifiedDate: Any,
    @SerializedName("religion")
    val religion: Religion,
    @SerializedName("sub_caste")
    val subCaste: String,
    @SerializedName("user_key")
    val userKey: Any,
    @SerializedName("worship_place_visit")
    val worshipPlaceVisit: Any,
    @SerializedName("worship_place_visit_text")
    val worshipPlaceVisitText: Any
)