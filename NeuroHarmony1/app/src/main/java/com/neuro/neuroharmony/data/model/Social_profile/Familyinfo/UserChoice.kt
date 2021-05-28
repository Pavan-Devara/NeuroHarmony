package com.neuro.neuroharmony.data.model.Social_profile.Familyinfo


import com.google.gson.annotations.SerializedName


data class UserChoice(
    @SerializedName("family_income")
    val familyIncome: Any,
    @SerializedName("family_type")
    val familyType: Any,
    @SerializedName("family_value")
    val familyValue: Any,
    @SerializedName("father_company")
    val fatherCompany: Any,
    @SerializedName("father_designation")
    val fatherDesignation: Any,
    @SerializedName("father_status")
    val fatherStatus: Any,
    @SerializedName("mother_company")
    val motherCompany: Any,
    @SerializedName("mother_designation")
    val motherDesignation: Any,
    @SerializedName("mother_status")
    val motherStatus: Any,
    @SerializedName("siblings")
    val siblings: Any
)