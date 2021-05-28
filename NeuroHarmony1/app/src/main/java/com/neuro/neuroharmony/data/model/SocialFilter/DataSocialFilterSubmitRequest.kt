package com.neuro.neuroharmony.data.model.SocialFilter

data class DataSocialFilterSubmitRequest(
    val caste: Any,
    val max_age: Any?,
    val max_height: Any?,
    val min_age: Any?,
    val min_height: Any?,
    val profession: Any,
    val religion: Any,
    val social_filter_active: Boolean
)