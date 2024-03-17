package com.kaajjo.orgtechservice.ui.screen.acconunt

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaajjo.orgtechservice.core.constants.ResponseConstants
import com.kaajjo.orgtechservice.data.local.datastore.UserDataStore
import com.kaajjo.orgtechservice.data.remote.api.user.UserService
import com.kaajjo.orgtechservice.data.remote.dto.Client
import com.kaajjo.orgtechservice.data.remote.dto.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userService: UserService,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _userInfo = MutableStateFlow<Client?>(null)
    val userInfo = _userInfo.asStateFlow()

    private val _userApiKey = mutableStateOf("")
    val userApiKey by _userApiKey


    private var _activeSessions = MutableStateFlow<List<Session>>(emptyList())
    val activeSessions = _activeSessions.asStateFlow()

    var isLoggedOut by mutableStateOf(false)
    var logoutError by mutableStateOf(false)

    init {
        userDataStore.userApiKey
            .onEach {
                withContext(Dispatchers.Main) { _userApiKey.value = it }
            }
            .launchIn(viewModelScope)

        loadActiveSessions()
        loadUserInfo(_userApiKey.value)
    }

    private fun loadUserInfo(key: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val userInfoResponse = userService.getUserInfo(key)
                    if (userInfoResponse.isSuccessful && userInfoResponse.body() != null && userInfoResponse.body()?.status == ResponseConstants.STATUS_OK) {
                        withContext(Dispatchers.Main) {
                            _userInfo.value = userInfoResponse.body()?.client
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            _userInfo.value = null
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Account LoadUserData", e.message.toString())
                }
            }
        }
    }
    private fun loadActiveSessions() {
        val key = userApiKey
        viewModelScope.launch(Dispatchers.IO) {
            val activeSessionsResponse = userService.getActiveSessions(key)

            if (activeSessionsResponse.isSuccessful && activeSessionsResponse.body() != null) {
                if (activeSessionsResponse.body()?.status == ResponseConstants.STATUS_OK) {
                    _activeSessions.value = activeSessionsResponse.body()?.sessions!!
                }
            }
        }
    }

    fun logout(key: String) {
        val currentUserKey = userApiKey
        viewModelScope.launch(Dispatchers.IO) {
            val logoutResponse = userService.logout(key)

            if (logoutResponse.isSuccessful && logoutResponse.body() != null) {
                if (logoutResponse.body()?.status == ResponseConstants.STATUS_OK) {
                    if (key == currentUserKey) {
                        // delete current user api key
                        userDataStore.setUserApiKey("")
                        isLoggedOut = true
                    } else {
                        loadActiveSessions()
                    }

                } else {
                    logoutError = true

                    Log.d(
                        "AccountViewModel",
                        "Can't logout. Response status is: ${logoutResponse.body()?.status ?: "null"}"
                    )
                }
            }
        }
    }
}