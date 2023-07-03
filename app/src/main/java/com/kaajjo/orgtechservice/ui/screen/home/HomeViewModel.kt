package com.kaajjo.orgtechservice.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaajjo.orgtechservice.core.constants.ResponseConstants
import com.kaajjo.orgtechservice.data.local.datastore.UserDataStore
import com.kaajjo.orgtechservice.data.remote.api.user.UserService
import com.kaajjo.orgtechservice.data.remote.dto.UserInfoDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userService: UserService,
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _userApiKey = mutableStateOf("")
    val userApiKey by _userApiKey

    var user by mutableStateOf<UserInfoDto?>(null)

    var getUserDataError by mutableStateOf(false)

    init {
        userDataStore.userApiKey
            .onEach {
                _userApiKey.value = it
                if (it.length == 32) {
                    fetchUserData(it)
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun fetchUserData(key: String) {
        withContext(Dispatchers.IO) {
            val userInfoResponse = userService.getUserInfo(key)
            if (userInfoResponse.isSuccessful && userInfoResponse.body() != null) {
                if (userInfoResponse.body()?.status == ResponseConstants.STATUS_OK) {
                    withContext(Dispatchers.Main) {
                        user = userInfoResponse.body()
                    }
                    getUserDataError = false
                } else {
                    getUserDataError = true
                }
            } else {
                getUserDataError = true
            }
        }
    }
}