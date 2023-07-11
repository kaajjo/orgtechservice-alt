package com.kaajjo.orgtechservice.ui.screen.traffic_monthly

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTitle
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTopAppBar
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.rememberTopAppBarScrollBehavior
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import korlibs.time.DateTime

@Destination
@Composable
fun TrafficMonthlyScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: TrafficMonthlyViewModel = hiltViewModel()
) {
    val traffic = viewModel.getTrafficMonthly().collectAsLazyPagingItems()
    val scrollBehavior = rememberTopAppBarScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CollapsingTopAppBar(
                collapsingTitle = CollapsingTitle.medium(titleText = "Трафик"),
                navigationIcon = {
                    IconButton(onClick = { destinationsNavigator.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        val monthlyTraffic by viewModel.monthlyTraffic.collectAsState()


        monthlyTraffic?.let {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = traffic.itemCount,
                    key = traffic.itemKey { it.hashCode() },
                ) { index ->
                    val item = traffic[index]
                    item?.let {
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    "С " + DateTime(it.dpStartDate * 1000.0).format("dd.MM.yyyy") + " по "
                                            + DateTime(it.dpEndDate * 1000.0).format("dd.MM.yyyy"),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text("Дневной " + (it.dailyRxBytes / 1024f / 1024f / 1024f).toString() + " ГиБ")
                                Text("Ночной " + (it.nightlyRxBytes / 1024f / 1024f / 1024f).toString() + " ГиБ")
                                Text("Локальный " + (it.localBytes / 1024f / 1024f / 1024f).toString() + " ГиБ")
                                Text("Исходящий  " + (it.txBytes / 1024f / 1024f / 1024f).toString() + " ГиБ")
                            }
                        }
                    }
                }
            }
        }
    }
}