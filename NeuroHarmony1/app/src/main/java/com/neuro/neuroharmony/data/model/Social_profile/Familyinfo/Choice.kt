package com.neuro.neuroharmony.data.model.Social_profile.Familyinfo


import com.google.gson.annotations.SerializedName


data class Choice(
    @SerializedName("family_income")
    val familyIncome: List<Familystatus>,
    @SerializedName("family_type")
    val familyType: List<Familystatus>,
    @SerializedName("family_value")
    val familyValue: List<Familystatus>,
    @SerializedName("father_company")
    val fatherCompany: List<Familystatus>,
    @SerializedName("father_designation")
    val fatherDesignation: List<Familystatus>,
    @SerializedName("father_status")
    val fatherStatus: List<Familystatus>,
    @SerializedName("mother_company")
    val motherCompany: List<Familystatus>,
    @SerializedName("mother_designation")
    val motherDesignation: List<Familystatus>,
    @SerializedName("mother_status")
    val motherStatus: List<Familystatus>,
    @SerializedName("siblings")
    val siblings: List<Familystatus>
)