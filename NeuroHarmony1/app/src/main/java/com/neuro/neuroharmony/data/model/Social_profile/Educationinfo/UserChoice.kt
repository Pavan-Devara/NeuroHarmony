package com.neuro.neuroharmony.data.model.Social_profile.Educationinfo


import com.google.gson.annotations.SerializedName


data class UserChoice(
    @SerializedName("employment_status")
    val employmentStatus: Any,
    @SerializedName("employment_status_description")
    val employmentStatusDescription: Any,
    @SerializedName("present_employer")
    val presentEmployer: Any,
    @SerializedName("present_profession")
    val presentProfession: Any,
    @SerializedName("qualification")
    val qualification: Any,
    @SerializedName("salary_range")
    val salaryRange: Any,
    @SerializedName("work_experience")
    val workExperience: Any
)