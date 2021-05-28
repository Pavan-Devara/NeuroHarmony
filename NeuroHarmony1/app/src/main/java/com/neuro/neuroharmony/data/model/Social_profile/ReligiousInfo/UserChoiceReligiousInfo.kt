package com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo


import com.google.gson.annotations.SerializedName


data class UserChoiceReligiousInfo(
    @SerializedName("caste")
    val caste: Any,
    @SerializedName("caste_name")
    val caste_name: Any,
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
    val religion: Any,
    @SerializedName("religion_name")
    val religion_name: Any,
    @SerializedName("sub_caste")
    val subCaste: Any,
    @SerializedName("sub_caste_name")
    val subCaste_name: Any,
    @SerializedName("sub_caste_text")
    val subCaste_text: Any,
    @SerializedName("user_key")
    val userKey: Any,
    @SerializedName("worship_place_visit")
    val worshipPlaceVisit: Any,
    @SerializedName("worship_place_visit_text")
    val worshipPlaceVisitText: Any
)