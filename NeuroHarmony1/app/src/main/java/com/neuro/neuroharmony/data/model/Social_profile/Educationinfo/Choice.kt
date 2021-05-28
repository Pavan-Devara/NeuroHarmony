package com.neuro.neuroharmony.data.model.Social_profile.Educationinfo


import com.google.gson.annotations.SerializedName


data class Choice(
    @SerializedName("employment_status")
    val employmentStatus: List<EducationProfStatus>,
    @SerializedName("present_employer")
    val presentEmployer: List<EducationProfStatus>,
    @SerializedName("present_profession")
    val presentProfession: List<EducationProfStatus>,
    @SerializedName("qualification")
    val qualification: List<EducationProfStatus>,
    @SerializedName("salary_range")
    val salaryRange: List<EducationProfStatus>,
    @SerializedName("work_experience")
    val workExperience: List<EducationProfStatus>
)