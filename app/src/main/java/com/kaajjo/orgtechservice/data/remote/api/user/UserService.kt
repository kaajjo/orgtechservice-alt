package com.kaajjo.orgtechservice.data.remote.api.user

import com.kaajjo.orgtechservice.data.remote.dto.UserInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    // https://mkpnet.ru/cabinet/api.php?call=info&key=KEY
    @GET("api.php?call=info")
    suspend fun getUserInfo(
        @Query("key") key: String
    ): Response<UserInfoDto>
}