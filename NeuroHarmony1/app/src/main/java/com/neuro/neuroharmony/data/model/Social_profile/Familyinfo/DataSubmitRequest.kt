package com.neuro.neuroharmony.data.model.Social_profile.Familyinfo


import com.google.gson.annotations.SerializedName


data class DataSubmitRequest(
    @SerializedName("family_income")
    val familyIncome: String,
    @SerializedName("family_type")
    val familyType: String,
    @SerializedName("family_value")
    val familyValue: String,
    @SerializedName("father_company")
    val fatherCompany: String,
    @SerializedName("father_designation")
    val fatherDesignation: String,
    @SerializedName("father_status")
    val fatherStatus: String,
    @SerializedName("mother_company")
    val motherCompany: String,
    @SerializedName("mother_designation")
    val motherDesignation: String,
    @SerializedName("mother_status")
    val motherStatus: String,
    @SerializedName("siblings")
    val siblings: String
)