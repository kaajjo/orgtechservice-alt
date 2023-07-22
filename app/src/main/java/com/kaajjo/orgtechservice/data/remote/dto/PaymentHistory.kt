package com.kaajjo.orgtechservice.data.remote.dto
import com.google.gson.annotations.SerializedName


data class PaymentsHistory(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages_count")
    val pagesCount: Int,
    @SerializedName("payments")
    val payments: List<PaymentHistoryItem>,
    @SerializedName("status")
    val status: String
)

data class PaymentHistoryItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("sum")
    val sum: Double,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("terminal")
    val terminal: String,
    @SerializedName("timestamp")
    val timestamp: Int
)