package com.kaajjo.orgtechservice.data.remote.dto
import com.google.gson.annotations.SerializedName


data class Tariffs(
    @SerializedName("comment")
    val comment: String,
    @SerializedName("last_date")
    val lastDate: String,
    @SerializedName("service_id")
    val serviceId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tariffs")
    val tariffs: List<TariffFull>
)

/**
 * @property speedMbps Скорость интернета в Мбит/с
 * @property speedMbpsAfterQuota Скорость интернета в Мбит/с при исчерпании трафика. Пустая строка, если безлимит
 * @property quota Количество трафика в ГиБ. Пустая строка, если безлимит
 * @property freeIptv Предоставляется ли бесплатная IPTV приставка. 0 - нет, 1 - да
 */
data class TariffFull(
    @SerializedName("router_name")
    val routerName: String,
    @SerializedName("tariff_cost")
    val cost: String,
    @SerializedName("tariff_deleted")
    val isDeleted: String,
    @SerializedName("tariff_dialup_id")
    val dialupId: String,
    @SerializedName("tariff_id")
    val id: String,
    @SerializedName("tariff_ipservice_id")
    val tariffIpserviceId: String,
    @SerializedName("tariff_name")
    val fullName: String,
    @SerializedName("tariff_periodic_id")
    val tariffPeriodicId: Any?,
    @SerializedName("ts_basic_tariff")
    val tsBasicTariff: String,
    @SerializedName("ts_discount_duration")
    val tsDiscountDuration: String,
    @SerializedName("ts_dynamic_tariff_id")
    val tsDynamicTariffId: String,
    @SerializedName("ts_group_id")
    val tsGroupId: String,
    @SerializedName("ts_id")
    val tsId: String,
    @SerializedName("ts_installment")
    val tsInstallment: String,
    @SerializedName("ts_move_from")
    val tsMoveFrom: String,
    @SerializedName("ts_move_to")
    val tsMoveTo: String,
    @SerializedName("ts_router_model")
    val tsRouterModel: String,
    @SerializedName("ts_social")
    val tsSocial: String,
    @SerializedName("ts_static_tariff_id")
    val tsStaticTariffId: String,
    @SerializedName("ts_tariff_camera")
    val tsTariffCamera: String,
    @SerializedName("ts_tariff_cost")
    val tsTariffCost: Float,
    @SerializedName("ts_tariff_name")
    val displayName: String,
    @SerializedName("ts_tariff_pay_type")
    val tsTariffPayType: String,
    @SerializedName("ts_tariff_quota")
    val quota: String,
    @SerializedName("ts_tariff_router")
    val tsTariffRouter: String,
    @SerializedName("ts_tariff_speed")
    val speedMbps: String,
    @SerializedName("ts_tariff_speed_after_quota")
    val speedMbpsAfterQuota: String,
    @SerializedName("ts_tariff_stb")
    val freeIptv: String,
    @SerializedName("ts_tariff_type")
    val tsTariffType: String,
    @SerializedName("ts_tariff_video_store")
    val tsTariffVideoStore: String
)