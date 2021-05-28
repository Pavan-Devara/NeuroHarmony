package com.neuro.neuroharmony.data.model.GenericData


import com.google.gson.annotations.SerializedName


data class Data(
    @SerializedName("device_info")
    val deviceInfo: DeviceInfo
)