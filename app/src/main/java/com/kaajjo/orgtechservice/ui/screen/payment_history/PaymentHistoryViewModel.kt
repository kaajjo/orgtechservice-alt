package com.kaajjo.orgtechservice.ui.screen.payment_history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kaajjo.orgtechservice.data.local.datastore.UserDataStore
import com.kaajjo.orgtechservice.data.remote.api.payment.PaymentService
import com.kaajjo.orgtechservice.data.remote.dto.PaymentHistoryItem
import com.kaajjo.orgtechservice.data.remote.paging.payments.PaymentsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PaymentHistoryViewModel @Inject constructor(
    private val paymentService: PaymentService,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _userApiKey = mutableStateOf("")
    val userApiKey by _userApiKey

    init {
        userDataStore.userApiKey
            .onEach {
                withContext(Dispatchers.Main) {
                    _userApiKey.value = it
                }

            }
            .launchIn(viewModelScope)
    }

    fun getPaymentsHistory(): Flow<PagingData<PaymentHistoryItem>> = Pager(
        config = PagingConfig(
            pageSize = 11
        ),
        pagingSourceFactory = {
            PaymentsPagingSource(
                paymentService = paymentService,
                key = userApiKey
            )
        }
    ).flow.cachedIn(viewModelScope)
}