package com.nikasov.waterbalance.ui.fragment.main.statistic

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.nikasov.waterbalance.R
import com.nikasov.waterbalance.utils.CustomMarkerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistic.*

@AndroidEntryPoint
class StatisticFragment : Fragment(R.layout.fragment_statistic) {

    private val viewModel : StatisticViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChart(weekBarChart)
        initChart(monthBarChart)
        initStat()
    }

    private fun initStat() {
        viewModel.waterIntakes.observe(viewLifecycleOwner, Observer {
            viewModel.getStatByDays(StatisticViewModel.StatState.MONTH)
            viewModel.getStatByDays(StatisticViewModel.StatState.WEEK)
        })
        viewModel.waterIntakesMapByWeek.observe(viewLifecycleOwner, Observer { intakesMap ->
            updateUi()
        })
        viewModel.waterIntakesMapByMonth.observe(viewLifecycleOwner, Observer { intakesMap ->
            updateUi()
        })
    }

    private fun updateUi() {
        updateWeekChart()
        updateMonthChart()
        updateAllStat()
    }

    private fun updateAllStat() {
        allWaterIntakesAmount.text = viewModel.allWaterIntakesAmount
        avgWaterIntakesAmountByDay.text = viewModel.avgWaterIntakesAmountByDay
        avgWaterIntakesCountByDay.text = viewModel.avgWaterIntakesCountByDay
    }

    private fun updateMonthChart() {
        monthTitle.text = viewModel.monthText

        val barDataSet = BarDataSet(viewModel.monthEntryList, "Water intakes by month")
        barDataSet.valueTextColor = android.R.color.transparent
        barDataSet.color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)

        val iBarDataSet = arrayListOf<IBarDataSet>()
        iBarDataSet.add(barDataSet)

        val xAxisLabel = viewModel.monthDaysString
        monthBarChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)

        monthBarChart.data = BarData(iBarDataSet)
        monthBarChart.invalidate()
    }

    private fun updateWeekChart() {

        weekTitle.text = viewModel.weekDateRange

        val barDataSet = BarDataSet(viewModel.weekEntryList, "Water intakes by week")
        barDataSet.valueTextColor = android.R.color.transparent
        barDataSet.color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)

        val iBarDataSet = arrayListOf<IBarDataSet>()
        iBarDataSet.add(barDataSet)

        val xAxisLabel = viewModel.weekDaysString
        weekBarChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)

        weekBarChart.data = BarData(iBarDataSet)
        weekBarChart.invalidate()
    }

    private fun initChart(chart: BarChart) {

        val mv = CustomMarkerView(requireContext(), R.layout.chart_marker_view)

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)

        chart.setFitBars(true)
        chart.animateY(1000)

        chart.axisRight.setDrawLabels(false)
        chart.legend.isEnabled = false
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)

        chart.isDragEnabled = false
        chart.setScaleEnabled(false)
        chart.setPinchZoom(false)
        chart.isAutoScaleMinMaxEnabled = false

        chart.isHighlightPerTapEnabled = true

        chart.marker = mv
    }
}