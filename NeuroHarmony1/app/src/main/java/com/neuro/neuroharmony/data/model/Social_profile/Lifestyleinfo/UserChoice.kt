package com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo


import com.google.gson.annotations.SerializedName

data class UserChoice(
    @SerializedName("alcohol")
    val alcohol: Any,
    @SerializedName("dietary")
    val dietary: Any,
    @SerializedName("pet")
    val pet: Any,
    @SerializedName("post_working")
    val postWorking: Any,
    @SerializedName("smoke")
    val smoke: Any,
    @SerializedName("sunglasses")
    val sunglasses: Any
)