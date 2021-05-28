package com.neuro.neuroharmony.data.model.Social_profile.Educationinfo


import com.google.gson.annotations.SerializedName


data class DataSubmitRequest(
    @SerializedName("employment_status")
    val employmentStatus: String,
    @SerializedName("employment_status_description")
    val employmentStatusDescription: String,
    @SerializedName("present_employer")
    val presentEmployer: String,
    @SerializedName("present_profession")
    val presentProfession: String,
    @SerializedName("qualification")
    val qualification: String,
    @SerializedName("salary_range")
    val salaryRange: String,
    @SerializedName("work_experience")
    val workExperience: String
)