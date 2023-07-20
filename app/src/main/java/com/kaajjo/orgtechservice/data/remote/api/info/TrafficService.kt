package com.kaajjo.orgtechservice.data.remote.api.info

import com.kaajjo.orgtechservice.data.remote.dto.TrafficMonth
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrafficService {
    // https://mkpnet.ru/cabinet/api.php?call=traffic_all&key=bd429d7b4d2269d080cc0e1cc422680c&page=0
    @GET("api.php?call=traffic_all")
    suspend fun getTrafficMonthly(
        @Query("key") key: String,
        @Query("page") page: Int = 1
    ): Response<TrafficMonth>
}