package com.kaajjo.orgtechservice.data.remote.dto
import com.google.gson.annotations.SerializedName


data class TrafficMonth(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages_count")
    val pagesCount: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("traffic")
    val traffic: List<Traffic>
)

data class Traffic(
    @SerializedName("daily_rx_bytes")
    val dailyRxBytes: Long,
    @SerializedName("discount_period_id")
    val discountPeriodId: Long,
    @SerializedName("dp_end_date")
    val dpEndDate: Int,
    @SerializedName("dp_start_date")
    val dpStartDate: Int,
    @SerializedName("local_bytes")
    val localBytes: Long,
    @SerializedName("nightly_rx_bytes")
    val nightlyRxBytes: Long,
    @SerializedName("slink_id")
    val slinkId: String,
    @SerializedName("tariff_name")
    val tariffName: String,
    @SerializedName("tx_bytes")
    val txBytes: Long
)