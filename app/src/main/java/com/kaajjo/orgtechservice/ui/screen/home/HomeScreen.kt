package com.kaajjo.orgtechservice.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaajjo.orgtechservice.R
import com.kaajjo.orgtechservice.data.remote.dto.Account
import com.kaajjo.orgtechservice.data.remote.dto.Tariff
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import korlibs.time.DateTime
import kotlin.math.roundToInt

@Destination
@Composable
fun HomeScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column {
        viewModel.user?.let { user ->
            Text(
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
            )

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
                        } }
                }
            }
        }
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