package com.kaajjo.orgtechservice.data.remote.api.info

import com.kaajjo.orgtechservice.data.remote.dto.Tariffs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TariffService {
    @GET("api.php?call=tariffs")
    suspend fun getTariffs(
        @Query("key") key: String
    ): Response<Tariffs>
}