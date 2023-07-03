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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userService: UserService,
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _userApiKey = mutableStateOf("")
    val userApiKey by _userApiKey

    var isLoggedOut by mutableStateOf(false)
    var logoutError by mutableStateOf(false)

    init {
        userDataStore.userApiKey
            .onEach {
                _userApiKey.value = it
            }
            .launchIn(viewModelScope)
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            val logoutResponse = userService.logout(userApiKey)

            if (logoutResponse.isSuccessful && logoutResponse.body() != null) {
                if (logoutResponse.body()?.status == ResponseConstants.STATUS_OK) {
                    userDataStore.setUserApiKey("") // delete user api key
                    isLoggedOut = true
                } else {
                    logoutError = true

                    Log.d(
                        "AccountViewModel",
                        "Can't logout. Response satus is: ${logoutResponse.body()?.status ?: "null"}"
                    )
                }
            }
        }
    }
}