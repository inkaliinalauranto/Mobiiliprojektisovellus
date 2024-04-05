package com.example.coolbox_mobiiliprojekti_app.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.model.rememberMarker
import com.example.coolbox_mobiiliprojekti_app.ui.theme.CoolBoxmobiiliprojektiAppTheme
import com.example.datachartexample2.tests.test3.ConsumptionViewModel
import com.example.datachartexample2.tests.test3.formatToDateToDayOfWeek
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.columnSeries
import com.patrykandpatrick.vico.core.model.lineSeries


@Composable
fun ConsumptionColumnChart(
    consumptionStatsData: Map<String, Float?>?,
    temperatureStatsData: Map<String, Float?>?,
    maxValue: Float = 10f
) {

    // Haetaan viewmodel
    val viewModel: ConsumptionViewModel = viewModel()

    // Luodaan modelProducer, joka vastaa chartin datan käsittelystä
    val modelProducer = remember { CartesianChartModelProducer.build() }

    // Avain labelien tallentamiseen extraStoreen
    val labelListKey = remember { ExtraStore.Key<List<String>>() }

    // Määritetään akselin arvojen muotoilu
    val valueFormatterString = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, chartValues, _ ->
        chartValues.model.extraStore[labelListKey]?.get(x.toInt()) ?: ""
    }

    // Käynnistetään effect, joka reagoi consumptionStatsData:n ja temperatureStatsData:n muutoksiin
    LaunchedEffect(key1 = consumptionStatsData, key2 = temperatureStatsData) {
        viewModel.consumptionStatsData?.let { consumptionStatsData ->
            viewModel.temperatureStatsData?.let { temperatureStatsData ->
                // Yritetään suorittaa transaktio modelProducerilla
                modelProducer.tryRunTransaction {
                    // Haetaan datan avaimet ja arvot listoiksi
                    val dates = consumptionStatsData.keys.toList()
                    val consumptions = consumptionStatsData.values.toList()
                    val temperatures = temperatureStatsData.values.toList()

                    // Tulostetaan dataa debug-tarkoituksissa
                    Log.d("Dorian", "dates $dates   consumptions $consumptions   temperatures $temperatures")

                    // Muotoillaan päivämäärät päivän nimiksi
                    val datesFormatted = formatToDateToDayOfWeek(dates)
                    Log.d("Dorian", "dates $datesFormatted   consumptions $consumptions")

                    // Luodaan sarakkeet kulutusdatalle
                    columnSeries {
                        series(consumptions)
                    }
                    // Luodaan viivat lämpötiladatalle
                    lineSeries {
                        series(temperatures)
                    }
                    // Päivitetään extras lisäämällä päivämäärät
                    updateExtras {
                        it[labelListKey] = datesFormatted
                    }
                }
            }
        }
    }

    // Haetaan sarakkeiden määrä datalta
    val columnCount = viewModel.consumptionStatsData?.size ?: 0

    // Luodaan teema
    CoolBoxmobiiliprojektiAppTheme {
        // Pinta, joka kattaa koko näytön leveyden
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Sarake, joka täyttää koko leveyden
            Column(modifier = Modifier.fillMaxWidth()) {
                // Tekstimuotoinen nappi
                TextButton(
                    contentPadding = PaddingValues(0.dp),
                    shape = RectangleShape,
                    onClick = { /* TODO Open Total Consumption */ }
                ) {
                    // Kortti, joka toimii paneelina
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .wrapContentSize(Alignment.Center),
                        colors = CardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContentColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        // Teksti paneelin keskelle
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 20.dp),
                            fontSize = 20.sp,
                            text = "Total Consumption"
                        )
                        // CartesianChartHost, joka sisältää chartin
                        CartesianChartHost(
                            chart =
                            rememberCartesianChart(
                                rememberColumnCartesianLayer(
                                    columns = listOf(
                                        rememberLineComponent(
                                            color = Color.Blue,
                                            thickness = 8.dp, // Adjust as needed
                                        ),
                                        rememberLineComponent(
                                            color = Color.Blue,
                                            thickness = 8.dp, // Adjust as needed
                                        )
                                    ),
                                ),
                                rememberLineCartesianLayer(
                                    lines = listOf(
                                        rememberLineSpec(
                                            shader = DynamicShaders.color(Color.Red)
                                        ),
                                        rememberLineSpec(
                                            shader = DynamicShaders.color(Color.Red)
                                        )
                                    ),
                                ),
                                startAxis = rememberStartAxis(),
                                bottomAxis =
                                rememberBottomAxis(
                                    valueFormatter = valueFormatterString,
                                    itemPlacer =
                                    remember {
                                        AxisItemPlacer.Horizontal.default(
                                            spacing = 1,
                                            addExtremeLabelPadding = true
                                        )
                                    },
                                ),
                            ),
                            marker = rememberMarker(),
                            modelProducer = modelProducer,
                            horizontalLayout = HorizontalLayout.fullWidth(),
                        )
                    }
                } // Paneeli loppuu

            } // Sarake loppuu
        }
    }
}
