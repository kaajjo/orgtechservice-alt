package com.kaajjo.orgtechservice.ui.screen.traffic_monthly

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kaajjo.orgtechservice.data.local.datastore.UserDataStore
import com.kaajjo.orgtechservice.data.remote.api.info.TrafficService
import com.kaajjo.orgtechservice.data.remote.dto.Traffic
import com.kaajjo.orgtechservice.data.remote.dto.TrafficMonth
import com.kaajjo.orgtechservice.data.remote.paging.traffic.TrafficPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TrafficMonthlyViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
    private val trafficService: TrafficService
) : ViewModel() {
    private val _userApiKey = mutableStateOf("")
    val userApiKey by _userApiKey

    private val _monthlyTraffic = MutableStateFlow<TrafficMonth?>(null)
    val monthlyTraffic = _monthlyTraffic.asStateFlow()


    // TODO: REFACTOR
    fun getTrafficMonthly(): Flow<PagingData<Traffic>> = Pager(
        config = PagingConfig(
            pageSize = 12
        ),
        pagingSourceFactory = {
            TrafficPagingSource(
                key = userApiKey,
                trafficService = trafficService
            )
        }
    ).flow.cachedIn(viewModelScope)

    private val currentPage = 1

    init {
        userDataStore.userApiKey
            .onEach {
                withContext(Dispatchers.Main) { _userApiKey.value = it }
                loadTraffic(userApiKey, currentPage)
            }
            .launchIn(viewModelScope)
    }

    private fun loadTraffic(key: String, page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            _monthlyTraffic.value = trafficService.getTrafficMonthly(key, page).body()
        }
    }
}