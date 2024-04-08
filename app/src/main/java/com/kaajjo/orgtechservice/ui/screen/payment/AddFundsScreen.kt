package com.kaajjo.orgtechservice.ui.screen.payment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.CurrencyRuble
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaajjo.orgtechservice.R
import com.kaajjo.orgtechservice.ui.component.ItemRowBigIcon
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTitle
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.CollapsingTopAppBar
import com.kaajjo.orgtechservice.ui.component.collapsing_topappbar.rememberTopAppBarScrollBehavior
import com.kaajjo.orgtechservice.ui.screen.destinations.PaymentsHistoryScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import korlibs.time.DateTime
import korlibs.time.KlockLocale
import korlibs.time.format
import korlibs.time.locale.russian
import kotlin.math.roundToInt

@Composable
@Destination
fun AddFundsScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: AddFundsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollBehavior = rememberTopAppBarScrollBehavior()
    val lastPayment by viewModel.lastPayment.collectAsState()
    val recommendedSum by viewModel.recommendedSum.collectAsState()
    var sum by rememberSaveable { mutableStateOf("10") }
    val userInfo by viewModel.userInfo.collectAsState()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CollapsingTopAppBar(
                collapsingTitle = CollapsingTitle.medium(titleText = "Платежи"),
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
        Column(
            Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                OutlinedTextField(
                    value = sum,
                    onValueChange = { sum = it },
                    label = { Text(stringResource(R.string.add_fund_label)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            Icons.Rounded.CurrencyRuble,
                            contentDescription = null
                        )
                    }
                )
                AnimatedVisibility(visible = recommendedSum > 0) {
                    Text(
                        text = "Рекомендуемая сумма - ${recommendedSum}₽",
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalContentColor.current.copy(alpha = 0.75f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Card(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .weight(1f)
                        .height(90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            4.dp
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = rememberVectorPainter(image = Icons.Rounded.Money),
                            contentDescription = "СБП",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Card(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .weight(1f)
                        .height(90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            4.dp
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.payanyway_logo),
                            contentDescription = "payanyway",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ItemRowBigIcon(
                    title = stringResource(id = R.string.payment_history_title),
                    icon = Icons.Rounded.History,
                    subtitle = if (lastPayment != null) {
                        "${lastPayment!!.sum.roundToInt()}₽, ${
                            DateTime(lastPayment!!.timestamp * 1000.0).format(
                                "d MMMM HH:mm",
                                KlockLocale.russian
                            )
                        }"
                    } else {
                        null
                    },
                    onClick = { destinationsNavigator.navigate(PaymentsHistoryScreenDestination) }
                )
                ItemRowBigIcon(
                    title = "Обещанный платеж",
                    subtitle = "Пополнить баланс за счет оператора сроком на 3 дня",
                    icon = Icons.Rounded.CreditCard
                )
                ItemRowBigIcon(
                    title = "Турбо режим",
                    subtitle = "Активировать дополнительный пакет трафика. История активаций",
                    onClick = { },
                    icon = Icons.Rounded.RocketLaunch
                )
            }
        }
    }
}