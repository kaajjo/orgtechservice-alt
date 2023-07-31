package com.kaajjo.orgtechservice.ui.screen.login

import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaajjo.orgtechservice.core.constants.ResponseConstants
import com.kaajjo.orgtechservice.core.utils.HashUtils
import com.kaajjo.orgtechservice.data.local.datastore.UserDataStore
import com.kaajjo.orgtechservice.data.remote.api.auth.AuthService
import com.kaajjo.orgtechservice.data.remote.dto.UserInfoDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val userDataStore: UserDataStore
) : ViewModel() {
    var login by mutableStateOf("")
    var password by mutableStateOf("")

    private val _userApiKey = mutableStateOf("")
    val userApiKey by _userApiKey

    var keyResponse by mutableStateOf<UserInfoDto?>(null)
    var authError by mutableStateOf(false)

    var isAuthenticated by mutableStateOf(false)
    var isAuthChecked = false

    var deviceName: String by mutableStateOf(Build.MODEL)

    var isAuthenticating by mutableStateOf(false)

    init {
        userDataStore.userApiKey
            .onEach { _userApiKey.value = it }
            .launchIn(viewModelScope)

        userDataStore.lastUsedLogin
            .onEach { login = it }
            .launchIn(viewModelScope)
    }

    fun auth() {
        isAuthChecked = false
        try {
            isAuthenticating = true

            viewModelScope.launch(Dispatchers.IO) {
                val authResponse = authService.authUser(
                    login = login,
                    password = HashUtils.createMD5(password),
                    device = deviceName,
                    deviceId = HashUtils.createMD5(Build.MODEL).take(16)
                )

                if (authResponse.isSuccessful) {
                    keyResponse = authResponse.body()
                    if (keyResponse?.status == null || keyResponse!!.status == ResponseConstants.STATUS_ERROR) {
                        authError = true
                    } else {
                        userDataStore.setUserApiKey(keyResponse?.key?.value ?: "")
                        userDataStore.setLastUsedLogin(login)
                    }
                } else {
                    authError = true
                }
                isAuthenticating = false
            }
        } catch (e: Exception) {
            Log.e("auth", e.message.toString())
            authError = true
        }
    }

    fun checkAuth(key: String) {
        // key length is always 32 symbols
        if (key.length != 32 || isAuthChecked || isAuthenticated) {
            return
        }

        isAuthenticating = true

        viewModelScope.launch(Dispatchers.IO) {
            val authCheckResponse = authService.checkAuth(key)
            if (authCheckResponse.isSuccessful) {
                if (authCheckResponse.body() != null) {
                    isAuthenticated =
                        authCheckResponse.body()!!.status == ResponseConstants.STATUS_OK
                }
                isAuthChecked = true
                isAuthenticating = false
            }
        }
    }
}