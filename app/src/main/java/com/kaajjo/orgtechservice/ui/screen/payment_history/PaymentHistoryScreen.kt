package com.kaajjo.orgtechservice.ui.screen.payment_history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.kaajjo.orgtechservice.R
import com.kaajjo.orgtechservice.data.remote.dto.PaymentHistoryItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import korlibs.time.DateTime
import korlibs.time.KlockLocale
import korlibs.time.format
import korlibs.time.locale.russian
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun PaymentsHistoryScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: PaymentHistoryViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val paymentsHistory = viewModel.getPaymentsHistory().collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.payment_history_title)) },
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

        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 12.dp)
        ) {
            items(
                count = paymentsHistory.itemCount,
                key = paymentsHistory.itemKey { it.timestamp }
            ) { index ->
                val item = paymentsHistory[index]
                item?.let { payment ->
                    PaymentItem(item = payment)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentItem(
    item: PaymentHistoryItem
) {
    var commentExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        onClick = { commentExpanded = !commentExpanded }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                Text(
                    text = item.terminal,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp
                )

                AnimatedVisibility(visible = commentExpanded) {
                    Text(
                        text = item.comment,
                        modifier = Modifier.padding(bottom = 6.dp),
                        fontSize = 14.sp
                    )
                }

                Text(
                    text = DateTime(item.timestamp * 1000.0).format(
                        "d MMMM yyyy HH:mm",
                        KlockLocale.russian
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalContentColor.current.copy(alpha = 0.75f)
                )
            }
            Text(
                text = DecimalFormat("0.##").format(item.sum) + " ₽",
                /*color = if (item.sum >= 0.0) {
                    LocalCustomColorsPalette.current.positiveText
                } else {
                    LocalCustomColorsPalette.current.negative
                },*/
                fontWeight = FontWeight.Medium
            )
        }
    }
}