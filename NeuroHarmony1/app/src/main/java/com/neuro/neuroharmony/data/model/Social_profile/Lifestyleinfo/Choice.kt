package com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo


import com.google.gson.annotations.SerializedName

data class Choice(
    @SerializedName("alcohol")
    val alcohol: List<Habits>,
    @SerializedName("dietary")
    val dietary: List<Habits>,
    @SerializedName("pet")
    val pet: List<Habits>,
    @SerializedName("post_working")
    val postWorking: List<Habits>,
    @SerializedName("smoke")
    val smoke: List<Habits>,
    @SerializedName("sunglasses")
    val sunglasses: List<Habits>
)