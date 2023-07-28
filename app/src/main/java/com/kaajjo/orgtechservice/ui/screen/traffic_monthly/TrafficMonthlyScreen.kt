package com.kaajjo.orgtechservice.ui.screen.traffic_monthly

import android.graphics.Typeface
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.kaajjo.orgtechservice.R
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.edges.rememberFadingEdges
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.overlayingComponent
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.chart.segment.SegmentProperties
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.extension.copyColor
import com.patrykandpatrick.vico.core.marker.Marker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import korlibs.time.DateTime
import korlibs.time.KlockLocale
import korlibs.time.format
import korlibs.time.locale.russian

private val axisValueOverrider =
    AxisValuesOverrider.adaptiveYValues(1.1f, round = true)

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TrafficMonthlyScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: TrafficMonthlyViewModel = hiltViewModel()
) {
    val traffic = viewModel.getTrafficMonthly().collectAsLazyPagingItems()
    val monthlyTraffic by viewModel.monthlyTraffic.collectAsState()
    /*val chartTrafficModel = entryModelOf(
        *(monthlyTraffic!!.traffic.take(6).map { it.dailyRxBytes / 1024f / 1024f / 1024f })
    )*/

    val marker = rememberMarker()
    val chartScrollState = rememberChartScrollState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.data_usage)) },
                navigationIcon = {
                    IconButton(onClick = { destinationsNavigator.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            monthlyTraffic?.let {
                item {
                    Column {
                        Text(
                            text = stringResource(R.string.traffic_graph_12_months),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        LaunchedEffect(Unit) {
                            chartScrollState.animateScrollBy(
                                value = chartScrollState.maxValue
                            )
                        }

                        ProvideChartStyle(m3ChartStyle()) {
                            Chart(
                                chart = lineChart(
                                    axisValuesOverrider = axisValueOverrider,
                                    spacing = 28.dp
                                ),
                                model = ChartEntryModelProducer(
                                    it.traffic.reversed()
                                        .mapIndexed { index, traffic ->
                                            Entry(
                                                DateTime(traffic.dpStartDate * 1000.0),
                                                index.toFloat(),
                                                traffic.dailyRxBytes / 1024f / 1024f / 1024f
                                            )
                                        }
                                ).getModel(),
                                startAxis = startAxis(maxLabelCount = 3),
                                bottomAxis = bottomAxis(valueFormatter = axisValueFormatterForDate()),
                                marker = marker,
                                fadingEdges = rememberFadingEdges(
                                    startEdgeWidth = 0.dp,
                                    endEdgeWidth = 16.dp
                                ),
                                chartScrollState = chartScrollState,
                                isZoomEnabled = false
                            )
                        }
                    }
                }
            }
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


@Composable
internal fun rememberMarker(): Marker {
    val labelBackgroundColor = MaterialTheme.colorScheme.surface
    val labelBackground = remember(labelBackgroundColor) {
        ShapeComponent(labelBackgroundShape, labelBackgroundColor.toArgb()).setShadow(
            radius = LABEL_BACKGROUND_SHADOW_RADIUS,
            dy = LABEL_BACKGROUND_SHADOW_DY,
            applyElevationOverlay = true,
        )
    }
    val label = textComponent(
        background = labelBackground,
        lineCount = LABEL_LINE_COUNT,
        padding = labelPadding,
        typeface = Typeface.MONOSPACE,
    )
    val indicatorInnerComponent =
        shapeComponent(Shapes.pillShape, MaterialTheme.colorScheme.surface)
    val indicatorCenterComponent = shapeComponent(Shapes.pillShape, Color.White)
    val indicatorOuterComponent = shapeComponent(Shapes.pillShape, Color.White)
    val indicator = overlayingComponent(
        outer = indicatorOuterComponent,
        inner = overlayingComponent(
            outer = indicatorCenterComponent,
            inner = indicatorInnerComponent,
            innerPaddingAll = indicatorInnerAndCenterComponentPaddingValue,
        ),
        innerPaddingAll = indicatorCenterAndOuterComponentPaddingValue,
    )
    val guideline = lineComponent(
        MaterialTheme.colorScheme.onSurface.copy(GUIDELINE_ALPHA),
        guidelineThickness,
        guidelineShape,
    )
    return remember(label, indicator, guideline) {
        object : MarkerComponent(label, indicator, guideline) {
            init {
                indicatorSizeDp = INDICATOR_SIZE_DP
                onApplyEntryColor = { entryColor ->
                    indicatorOuterComponent.color =
                        entryColor.copyColor(INDICATOR_OUTER_COMPONENT_ALPHA)
                    with(indicatorCenterComponent) {
                        color = entryColor
                        setShadow(
                            radius = INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS,
                            color = entryColor
                        )
                    }
                }
            }

            override fun getInsets(
                context: MeasureContext,
                outInsets: Insets,
                segmentProperties: SegmentProperties,
            ) = with(context) {
                outInsets.top =
                    label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels +
                            LABEL_BACKGROUND_SHADOW_RADIUS.pixels * SHADOW_RADIUS_MULTIPLIER -
                            LABEL_BACKGROUND_SHADOW_DY.pixels
            }
        }
    }
}

private const val LABEL_BACKGROUND_SHADOW_RADIUS = 4f
private const val LABEL_BACKGROUND_SHADOW_DY = 2f
private const val LABEL_LINE_COUNT = 1
private const val GUIDELINE_ALPHA = .2f
private const val INDICATOR_SIZE_DP = 36f
private const val INDICATOR_OUTER_COMPONENT_ALPHA = 32
private const val INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS = 12f
private const val GUIDELINE_DASH_LENGTH_DP = 8f
private const val GUIDELINE_GAP_LENGTH_DP = 4f
private const val SHADOW_RADIUS_MULTIPLIER = 1.3f

private val labelBackgroundShape = MarkerCorneredShape(Corner.FullyRounded)
private val labelHorizontalPaddingValue = 8.dp
private val labelVerticalPaddingValue = 4.dp
private val labelPadding = dimensionsOf(labelHorizontalPaddingValue, labelVerticalPaddingValue)
private val indicatorInnerAndCenterComponentPaddingValue = 5.dp
private val indicatorCenterAndOuterComponentPaddingValue = 10.dp
private val guidelineThickness = 2.dp
private val guidelineShape =
    DashedShape(Shapes.pillShape, GUIDELINE_DASH_LENGTH_DP, GUIDELINE_GAP_LENGTH_DP)


fun axisValueFormatterForDate(): AxisValueFormatter<AxisPosition.Horizontal.Bottom> =
    AxisValueFormatter { value, chartValues ->
        (chartValues.chartEntryModel.entries.firstOrNull()?.getOrNull(value.toInt()) as? Entry)
            ?.run {
                dateTime.format("MMM", KlockLocale.russian)
            }
            .orEmpty()
    }

class Entry(
    val dateTime: DateTime,
    override val x: Float,
    override val y: Float,
) : ChartEntry {

    override fun withY(y: Float) = Entry(
        dateTime = this.dateTime,
        x = this.x,
        y = y,
    )
}