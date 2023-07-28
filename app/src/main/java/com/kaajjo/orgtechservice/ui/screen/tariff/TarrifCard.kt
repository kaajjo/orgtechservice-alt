package com.kaajjo.orgtechservice.ui.screen.tariff

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.rounded.Cast
import androidx.compose.material.icons.rounded.DataUsage
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.kaajjo.orgtechservice.R
import com.kaajjo.orgtechservice.data.remote.dto.TariffFull
import com.kaajjo.orgtechservice.data.remote.dto.UserTariff
import com.kaajjo.orgtechservice.ui.component.TextWithLeadingIcon
import java.text.DecimalFormat
import kotlin.math.roundToInt

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
                    text = stringResource(R.string.user_loyalty_discount, userLoyalty)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = userTariff.cost.roundToInt().toString() + " ₽"
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