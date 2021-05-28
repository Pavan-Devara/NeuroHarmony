package com.neuro.neuroharmony.data.model.GenericData


import com.google.gson.annotations.SerializedName


data class DeviceInfo(
    @SerializedName("base_band_version")
    val baseBandVersion: String,
    @SerializedName("cpu")
    val cpu: String,
    @SerializedName("is_rooted_or_jail_broken")
    val isRootedOrJailBroken: String,
    @SerializedName("kernel_version")
    val kernelVersion: String,
    @SerializedName("model_name")
    val modelName: String,
    @SerializedName("model_number")
    val modelNumber: String,
    @SerializedName("os_type")
    val osType: Int,
    @SerializedName("os_version")
    val osVersion: String,
    @SerializedName("ram")
    val ram: String,
    @SerializedName("security_patch_level")
    val securityPatchLevel: String,
    @SerializedName("serial_number")
    val serialNumber: String,
    @SerializedName("storage_available")
    val storageAvailable: String,
    @SerializedName("storage_total")
    val storageTotal: String,
    @SerializedName("vendor_os_version")
    val vendorOsVersion: String
)