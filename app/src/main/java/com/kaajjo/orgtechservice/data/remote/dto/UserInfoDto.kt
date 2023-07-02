package com.kaajjo.orgtechservice.data.remote.dto
import com.google.gson.annotations.SerializedName


data class UserInfoDto(
    @SerializedName("client")
    val client: Client,
    @SerializedName("status")
    val status: String
)

data class Client(
    @SerializedName("account")
    val account: Account,
    @SerializedName("address")
    val address: String,
    @SerializedName("benefit")
    val benefit: Int,
    @SerializedName("benefit_type")
    val benefitType: String,
    @SerializedName("cameras")
    val cameras: Cameras,
    @SerializedName("city_id")
    val cityId: Int,
    @SerializedName("city_name")
    val cityName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("intercom")
    val intercom: Any,
    @SerializedName("is_juridical")
    val isJuridical: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("loyalty")
    val loyalty: Double,
    @SerializedName("mail")
    val mail: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("services")
    val services: List<Any>,
    @SerializedName("sip")
    val sip: Sip,
    @SerializedName("tariff")
    val tariff: Tariff,
    @SerializedName("updated_at")
    val updatedAt: Int,
    @SerializedName("year_discount_end")
    val yearDiscountEnd: String
)

data class Account(
    @SerializedName("balance")
    val balance: String,
    @SerializedName("blocking")
    val blocking: String,
    @SerializedName("credit")
    val credit: String,
    @SerializedName("credit_end")
    val creditEnd: Int,
    @SerializedName("discount_period")
    val discountPeriod: DiscountPeriod,
    @SerializedName("id")
    val id: Int,
    @SerializedName("vb_end")
    val vbEnd: Int,
    @SerializedName("vb_start")
    val vbStart: Int
)

data class Cameras(
    @SerializedName("granted")
    val granted: List<Any>,
    @SerializedName("own")
    val own: List<Any>,
    @SerializedName("shared")
    val shared: List<Any>
)

data class Sip(
    @SerializedName("sip_account")
    val sipAccount: Int,
    @SerializedName("sip_password")
    val sipPassword: String
)

data class Tariff(
    @SerializedName("cost")
    val cost: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("quota")
    val quota: Long,
    @SerializedName("slink_exist")
    val slinkExist: Int,
    @SerializedName("speed")
    val speed: Int,
    @SerializedName("traffic")
    val traffic: Long
)

data class DiscountPeriod(
    @SerializedName("end")
    val end: Int,
    @SerializedName("remaining_days")
    val remainingDays: String,
    @SerializedName("start")
    val start: Int
)
