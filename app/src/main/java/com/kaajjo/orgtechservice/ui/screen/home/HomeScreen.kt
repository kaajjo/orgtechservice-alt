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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.CloudDownload
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaajjo.orgtechservice.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import korlibs.time.DateTime

@Destination
@Composable
fun HomeScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column {
        viewModel.user?.let { user ->
            /*Text(
                text = stringResource(
                    R.string.hello_user_day,
                    user.client.name.trim().run {
                        this.substring(
                            this.indexOf(' ') + 1,
                            this.lastIndexOf(' ')
                        )
                    }
                ),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 12.dp)
            )*/

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
                        title = DateTime(user.client.account.discountPeriod.end * 1000.0).format("d MMMM"),
                        subtitle = "День оплаты"
                    )
                    AccountInfoCardItem(
                        title = "${
                            String.format(
                                "%.2f",
                                user.client.tariff.traffic / 1024f / 1024f / 1024f
                            )
                        } ГиБ",
                        subtitle = "Скачано"
                    )
                    AccountInfoCardItem(
                        title = user.client.tariff.speed.toString() + " Мбит/с",
                        subtitle = "Скорость"
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Divider(
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
                        text = "Баланс",
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
                        FilledIconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                DashboardItem(
                    icon = Icons.Rounded.List,
                    title = "Мой тариф",
                    trailingIcon = Icons.Rounded.ArrowForwardIos
                )
            }
            item {
                DashboardItem(
                    icon = Icons.Rounded.Videocam,
                    title = "Камеры",
                    trailingIcon = Icons.Rounded.ArrowForwardIos
                )
            }
            item {
                DashboardItem(
                    icon = Icons.Rounded.Lock,
                    title = "Блокировка",
                    trailingIcon = Icons.Rounded.ArrowForwardIos
                )
            }
            item {
                DashboardItem(
                    icon = Icons.Rounded.CloudDownload,
                    title = "Расход трафика",
                    trailingIcon = Icons.Rounded.ArrowForwardIos
                )
            }
            item {
                DashboardItem(
                    icon = Icons.Rounded.Bolt,
                    title = "Турбо режим",
                    trailingIcon = Icons.Rounded.ArrowForwardIos
                )
            }
        }
    }
}

@Composable
fun DashboardItem(
    icon: ImageVector,
    title: String,
    trailingIcon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
            .clickable { }
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