package com.kaajjo.orgtechservice.ui.screen.tariff

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Cast
import androidx.compose.material.icons.rounded.DataUsage
import androidx.compose.material3.Card
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaajjo.orgtechservice.R
import com.kaajjo.orgtechservice.data.remote.dto.TariffFull
import com.kaajjo.orgtechservice.data.remote.dto.UserTariff
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTitle
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTopAppBar
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.rememberTopAppBarScrollBehavior
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.DecimalFormat
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Destination
@Composable
fun TariffScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: TariffViewModel = hiltViewModel()
) {
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

        val types = listOf(
            "Все", "Turbo", "Unlim"
        )
        var selectedType by rememberSaveable {
            mutableStateOf(types.first())
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
                    items(types) {
                        FilterChip(
                            selected = selectedType == it,
                            onClick = { selectedType = it },
                            label = { Text(it) })
                    }
                }
            }
            items(
                when (selectedType) {
                    types[1] -> tariffs.filter { it.tsTariffType == "turbo" }
                    types[2] -> tariffs.filter { it.tsTariffType == "unlim" }
                    else -> tariffs /* TODO: make it better */
                }
            ) {
                Spacer(Modifier.height(12.dp))
                TariffCard(
                    tariff = it, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .animateItemPlacement(),
                    userLoyalty = userLoyalty
                )
            }
        }
    }
}

@Composable
fun TariffCard(
    userTariff: UserTariff,
    modifier: Modifier = Modifier,
    userLoyalty: Int = 0
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = userTariff.name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            TextWithLeadingIcon(
                text = userTariff.speed.toString() + " Мбит/с",
                icon = Icons.Default.RocketLaunch
            )
            Spacer(Modifier.height(12.dp))
            TextWithLeadingIcon(
                text = (userTariff.quota / 1024f / 1024f / 1024f)
                    .roundToInt()
                    .toString() + " ГиБ ",
                icon = Icons.Rounded.DataUsage
            )
            if (userLoyalty > 0) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Накопительная скидка $userLoyalty%"
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = userTariff.toString() + " ₽"
            )
        }
    }
}

@Composable
fun TariffCard(
    tariff: TariffFull,
    modifier: Modifier = Modifier,
    userLoyalty: Int = 0
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = tariff.displayName,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            TextWithLeadingIcon(
                text = tariff.speedMbps.toString() + " Мбит/с",
                icon = Icons.Default.RocketLaunch
            )
            Spacer(Modifier.height(12.dp))
            TextWithLeadingIcon(
                text ="${tariff.quota.ifEmpty { "∞" }} ГиБ",
                icon = Icons.Rounded.DataUsage
            )
            if (tariff.freeIptv == "1") {
                Spacer(Modifier.height(12.dp))
                TextWithLeadingIcon(text = "IPTV приставка бесплатно", icon = Icons.Rounded.Cast)
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    text = /*PriceFormatting.format(tariff.cost) + */"${tariff.cost} ₽",
                    style = MaterialTheme.typography.titleMedium.copy(
                        textDecoration = TextDecoration.LineThrough
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = DecimalFormat("0.##").format(tariff.cost.toFloat() - (tariff.cost.toFloat() / userLoyalty)) + " ₽",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun TextWithLeadingIcon(
    text: String,
    icon: ImageVector,
    contentDescription: String? = null
) {
    Row {
        Icon(imageVector = icon, contentDescription = contentDescription)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text
        )
    }
}