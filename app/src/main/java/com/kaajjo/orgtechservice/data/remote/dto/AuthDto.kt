package com.kaajjo.orgtechservice.data.remote.dto

data class AuthDto(
    val status: String?,
    val comment: String?,
    val key: Key
)

data class Key(
    val created: String,
    val device: String,
    val device_id: String,
    val expires: String,
    val value: String
)

