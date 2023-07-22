package com.kaajjo.orgtechservice.data.remote.api.payment

import com.kaajjo.orgtechservice.data.remote.dto.PaymentsHistory
import retrofit2.http.GET
import retrofit2.http.Query

interface PaymentService {

    @GET("api.php?call=payments")
    suspend fun getPaymentHistory(
        @Query("key") key: String,
        @Query("page") page: Int = 1,
    ): PaymentsHistory
}