package com.kaajjo.orgtechservice.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaajjo.orgtechservice.R
import com.kaajjo.orgtechservice.ui.component.ItemRowBigIcon
import com.kaajjo.orgtechservice.ui.screen.destinations.AccountScreenDestination
import com.kaajjo.orgtechservice.ui.screen.destinations.AddFundsScreenDestination
import com.kaajjo.orgtechservice.ui.screen.destinations.PaymentsHistoryScreenDestination
import com.kaajjo.orgtechservice.ui.screen.destinations.TariffScreenDestination
import com.kaajjo.orgtechservice.ui.screen.destinations.TrafficMonthlyScreenDestination
import com.kaajjo.orgtechservice.utils.formatter.DataSizeFormatter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import korlibs.time.DateTime

@Destination
@Composable
fun HomeScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Scaffold { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            viewModel.user?.let { user ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 12.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AccountInfoCardItem(
                            title = DateTime(user.client.account.discountPeriod.end * 1000.0).format(
                                "d MMMM"
                            ),
                            subtitle = stringResource(R.string.payment_day)
                        )
                        AccountInfoCardItem(
                            title = DataSizeFormatter().bytesReadable(
                                user.client.userTariff.traffic.toFloat(),
                                context
                            ),
                            subtitle = stringResource(R.string.data_downloaded)
                        )
                        AccountInfoCardItem(
                            title = user.client.userTariff.speed.toString() + " Мбит/с",
                            subtitle = stringResource(R.string.internet_speed)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .align(Alignment.CenterHorizontally)
                            .clip(CircleShape)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.account_balance),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = user.client.account.balance + " ₽",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(Modifier.width(8.dp))
                            FilledIconButton(onClick = {
                                destinationsNavigator.navigate(
                                    AddFundsScreenDestination
                                )
                            }) {
                                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                item {
                    viewModel.user?.let { user ->
                        DataUsageCard(
                            dataUsed = user.client.userTariff.traffic.toFloat(),
                            dataTotal = user.client.userTariff.quota.toFloat(),
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
                item {
                    ItemRowBigIcon(
                        title = stringResource(R.string.user_account),
                        icon = Icons.Rounded.Person,
                        onClick = { destinationsNavigator.navigate(AccountScreenDestination) },
                        titleStyle = MaterialTheme.typography.titleLarge
                    )
                }
                item {
                    ItemRowBigIcon(
                        title = stringResource(R.string.internet_plan),
                        icon = Icons.AutoMirrored.Rounded.List,
                        onClick = { destinationsNavigator.navigate(TariffScreenDestination) },
                        titleStyle = MaterialTheme.typography.titleLarge
                    )
                }
                item {
                    ItemRowBigIcon(
                        title = stringResource(R.string.label_data_usage),
                        icon = Icons.Rounded.Language,
                        onClick = { destinationsNavigator.navigate(TrafficMonthlyScreenDestination) },
                        titleStyle = MaterialTheme.typography.titleLarge
                    )
                }
                item {
                    ItemRowBigIcon(
                        title = stringResource(R.string.payment_history_title),
                        icon = Icons.Rounded.Wallet,
                        onClick = { destinationsNavigator.navigate(PaymentsHistoryScreenDestination) },
                        titleStyle = MaterialTheme.typography.titleLarge
                    )
                }
                item {
                    ItemRowBigIcon(
                        title = stringResource(R.string.user_self_block),
                        icon = Icons.Rounded.Lock,
                        titleStyle = MaterialTheme.typography.titleLarge
                    )
                }
                item {
                    ItemRowBigIcon(
                        title = stringResource(R.string.label_turbo_mode),
                        icon = Icons.Rounded.Bolt,
                        titleStyle = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardItem(
    icon: ImageVector,
    title: String,
    trailingIcon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Icon(imageVector = trailingIcon, contentDescription = null)
    }
}

@Composable
fun AccountInfoCardItem(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = subtitle,
            color = LocalContentColor.current.copy(alpha = 0.7f)
        )
    }
}