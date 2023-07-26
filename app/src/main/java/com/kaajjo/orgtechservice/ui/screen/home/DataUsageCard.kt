package com.kaajjo.orgtechservice.ui.screen.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaajjo.orgtechservice.R
import com.kaajjo.orgtechservice.ui.component.shape.WavyShape
import com.kaajjo.orgtechservice.ui.theme.colorBad
import com.kaajjo.orgtechservice.ui.theme.colorGood
import com.kaajjo.orgtechservice.ui.theme.colorNotGood
import com.kaajjo.orgtechservice.ui.theme.combineColors
import com.kaajjo.orgtechservice.ui.theme.harmonize
import com.kaajjo.orgtechservice.ui.theme.toPalette
import java.text.DecimalFormat

@Composable
fun DataUsageCard(
    dataUsed: Float,
    dataTotal: Float,
    modifier: Modifier = Modifier
) {
    val percent by remember(dataUsed, dataTotal) { mutableFloatStateOf(1f - dataUsed / dataTotal) }

    val infiniteAnimation = rememberInfiniteTransition(label = "DataUsageCard wavy infinite animation")
    val shift by infiniteAnimation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500, 0, LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "DataUsageCard wavy animation"
    )

    val harmonizedColor = toPalette(
        harmonize(
            combineColors(
                listOf(
                    colorBad,
                    colorNotGood,
                    colorGood,
                ),
                (percent + 0.25f).coerceIn(0f, 1f)
            )
        )
    )

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = harmonizedColor.container,
            contentColor = harmonizedColor.onContainer
        )
    ) {
        Box(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(percent.coerceIn(0f, 1f))
                        .background(
                            color = harmonizedColor.main,
                            shape = WavyShape(
                                period = 40.dp,
                                amplitude = 2.dp,
                                shift = shift
                            )
                        )
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text =
                    DecimalFormat("0.##").format((dataTotal - dataUsed) / 1024f / 1024f / 1024f)
                            + " ГиБ",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = stringResource(R.string.usage_data_left),
                    style = MaterialTheme.typography.labelMedium
                )

            }
        }
    }
}