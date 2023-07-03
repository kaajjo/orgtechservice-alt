package com.kaajjo.orgtechservice.data.remote.dto
import com.google.gson.annotations.SerializedName


data class ActiveSessionsDto(
    @SerializedName("keys")
    val sessions: List<Session>,
    @SerializedName("status")
    val status: String
)

data class Session(
    @SerializedName("created")
    val created: Long,
    @SerializedName("device")
    val device: String,
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("expires")
    val expires: Long,
    @SerializedName("value")
    val value: String
)