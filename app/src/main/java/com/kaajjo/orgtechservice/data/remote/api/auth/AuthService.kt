package com.kaajjo.orgtechservice.data.remote.api.auth

import com.kaajjo.orgtechservice.data.remote.dto.AuthCheckDto
import com.kaajjo.orgtechservice.data.remote.dto.AuthDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthService {

    // https://mkpnet.ru/cabinet/api.php?call=auth&username=USERNAME&password=PASSWORD_HASH&device=DEVICE&device_id=DEVICE_ID
    @GET("api.php?call=auth")
    suspend fun authUser(
        @Query("username") login: String,
        @Query("password") password: String,
        @Query("device") device: String,
        @Query("device_id") deviceId: String,
    ): Response<AuthDto>?

    // https://mkpnet.ru/cabinet/api.php?call=check&key=KEY
    @GET("api.php?call=check")
    suspend fun checkAuth(
        @Query("key") key: String
    ): Response<AuthCheckDto>
}