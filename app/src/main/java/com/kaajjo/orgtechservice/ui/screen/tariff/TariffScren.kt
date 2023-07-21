package com.kaajjo.orgtechservice.ui.screen.tariff

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaajjo.orgtechservice.R
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTitle
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTopAppBar
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.rememberTopAppBarScrollBehavior
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Destination
@Composable
fun TariffScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: TariffViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollBehavior = rememberTopAppBarScrollBehavior()
    val tariffs by viewModel.tariffs.collectAsState()
    val userLoyalty by viewModel.userLoyalty.collectAsState()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CollapsingTopAppBar(
                collapsingTitle = CollapsingTitle.medium(titleText = stringResource(R.string.label_tariffs)),
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

        val types by remember(tariffs) {
            mutableStateOf(
                mapOf("" to context.getString(R.string.filter_all)) + tariffs.map { it.tsTariffType }.distinct().associateWith {
                    when (it) {
                        "turbo" -> context.getString(R.string.tarrif_type_turbo)
                        "unlim" -> context.getString(R.string.tarrif_type_unlim)
                        else -> it.replaceFirstChar { it.uppercaseChar() }
                    }
                }
            )
        }
        var selectedType by rememberSaveable {
            mutableStateOf(types.asSequence().first().key)
        }

        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                viewModel.user?.client?.userTariff?.let {
                    Text(
                        text = stringResource(R.string.label_current_tariff),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                    TariffCard(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
                        userLoyalty = userLoyalty,
                        userTariff = it
                    )
                }
            }
            item {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.label_all_tariffs),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
            item {
                Spacer(Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(start = 12.dp)
                ) {
                    items(types.toList()) {
                        FilterChip(
                            selected = selectedType == it.first,
                            onClick = { selectedType = it.first },
                            label = { Text(it.second) }
                        )
                    }
                }
            }
            items(
                if (types.size > 1 && selectedType.isNotEmpty()) {
                    tariffs.filter { it.tsTariffType == selectedType }
                } else {
                    tariffs
                }
            ) {
                Spacer(Modifier.height(12.dp))
                TariffCard(
                    tariff = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .animateItemPlacement(),
                    userLoyalty = userLoyalty
                )
            }
        }
    }
}