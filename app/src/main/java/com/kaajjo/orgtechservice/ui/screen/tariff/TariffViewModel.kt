package com.kaajjo.orgtechservice.ui.screen.tariff

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaajjo.orgtechservice.core.constants.ResponseConstants
import com.kaajjo.orgtechservice.data.local.datastore.UserDataStore
import com.kaajjo.orgtechservice.data.remote.api.info.TariffService
import com.kaajjo.orgtechservice.data.remote.api.user.UserService
import com.kaajjo.orgtechservice.data.remote.dto.TariffFull
import com.kaajjo.orgtechservice.data.remote.dto.UserInfoDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class TariffViewModel @Inject constructor(
    private val userService: UserService,
    private val tariffService: TariffService,
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _userApiKey = mutableStateOf("")
    val userApiKey by _userApiKey

    var user by mutableStateOf<UserInfoDto?>(null)

    var getUserDataError by mutableStateOf(false)

    private var _tariffs = MutableStateFlow<List<TariffFull>>(emptyList())
    var tariffs = _tariffs.asStateFlow()

    private var _userLoyalty = MutableStateFlow<Int>(0)
    var userLoyalty = _userLoyalty.asStateFlow()

    init {
        userDataStore.userApiKey
            .onEach {
                _userApiKey.value = it
                if (it.length == 32) {
                    fetchUserData(it)
                    fetchTariffsData(it)
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun fetchUserData(key: String) {
        withContext(Dispatchers.IO) {
            val userInfoResponse = userService.getUserInfo(key)
            if (userInfoResponse.isSuccessful && userInfoResponse.body() != null && userInfoResponse.body()?.status == ResponseConstants.STATUS_OK) {
                withContext(Dispatchers.Main) {
                    user = userInfoResponse.body()
                    _userLoyalty.value = user?.client?.loyalty?.roundToInt() ?: 0
                }
                getUserDataError = false
            } else {
                getUserDataError = true
            }
        }
    }

    private suspend fun fetchTariffsData(key: String) {
        withContext(Dispatchers.IO) {
            val tariffResponse = tariffService.getTariffs(key)
            if (tariffResponse.isSuccessful && tariffResponse.body() != null && tariffResponse.body()?.status == ResponseConstants.STATUS_OK) {
                withContext(Dispatchers.Main) {
                    _tariffs.value = tariffResponse.body()!!.tariffs
                }
            }
        }
    }
}