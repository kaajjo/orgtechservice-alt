package com.kaajjo.orgtechservice.ui.screen.payment

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaajjo.orgtechservice.data.local.datastore.UserDataStore
import com.kaajjo.orgtechservice.data.remote.api.payment.PaymentService
import com.kaajjo.orgtechservice.data.remote.api.user.UserService
import com.kaajjo.orgtechservice.data.remote.dto.Client
import com.kaajjo.orgtechservice.data.remote.dto.PaymentHistoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class AddFundsViewModel @Inject constructor(
    userDataStore: UserDataStore,
    private val paymentService: PaymentService,
    private val userService: UserService
) : ViewModel() {
    private val _userApiKey = mutableStateOf("")
    val userApiKey by _userApiKey

    private var _lastPayment = MutableStateFlow<PaymentHistoryItem?>(null)
    var lastPayment = _lastPayment.asStateFlow()

    private var _recommendedSum = MutableStateFlow(0)
    var recommendedSum = _recommendedSum.asStateFlow()

    private var _userInfo = MutableStateFlow<Client?>(null)
    var userInfo = _userInfo.asStateFlow()

    init {
        userDataStore.userApiKey
            .onEach {
                _userApiKey.value = it
                if (it.length == 32) {
                    fetchHistory(it)
                    calculateRecommendedSum(it)
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun fetchHistory(key: String) {
        try {
            val historyResponse = paymentService.getPaymentHistory(key)
            if (historyResponse.payments.isNotEmpty()) {
                _lastPayment.value = historyResponse.payments.first()
            }
        } catch (e: Exception) {
            Log.e("fetchPaymentsHistory Funds", e.message.toString())
        }
    }

    private suspend fun calculateRecommendedSum(key: String) {
        try {
            val userInfoResponse = userService.getUserInfo(key)
            _userInfo.value = userInfoResponse.body()?.client

            if (userInfoResponse.isSuccessful && userInfoResponse.body() != null) {
                userInfoResponse.body()?.let { userInfo ->
                    val currentBalance = userInfo.client.account.balance.toFloat()
                    _recommendedSum.value = ceil(userInfo.client.userTariff.cost - currentBalance).toInt()
                }
            }
        } catch (e: Exception) {
            Log.e("calculateRecommendedSum Funds", e.message.toString())
        }
    }
}