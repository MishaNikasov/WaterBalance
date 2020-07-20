package com.nikasov.waterbalance.ui.fragment.main.statistic

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

        initChart()
        initStat()
    }

    private fun initStat() {
        viewModel.waterIntakes.observe(viewLifecycleOwner, Observer {
            viewModel.getStatByDays()
        })
        viewModel.waterIntakesMapByDay.observe(viewLifecycleOwner, Observer { intakesMap ->
            updateUi()
        })
    }

    private fun updateUi() {
        updateLineChart()
        dateTxt.text = viewModel.dateRange
    }

    private fun updateLineChart() {

        val barDataSet = BarDataSet(viewModel.entryList, "Water intakes")
        barDataSet.valueTextColor = android.R.color.transparent
        barDataSet.color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)

        val iBarDataSet = arrayListOf<IBarDataSet>()
        iBarDataSet.add(barDataSet)

        val xAxisLabel = viewModel.daysString
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)

        barChart.data = BarData(iBarDataSet)
        barChart.invalidate()
    }

    private fun initChart() {

        val mv = CustomMarkerView(requireContext(), R.layout.chart_marker_view)

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)

        barChart.setFitBars(true)
        barChart.animateY(1000)

        barChart.axisRight.setDrawLabels(false)
        barChart.legend.isEnabled = false
        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)

        barChart.isDragEnabled = false
        barChart.setScaleEnabled(false)
        barChart.setPinchZoom(false)
        barChart.isAutoScaleMinMaxEnabled = false

        barChart.isHighlightPerTapEnabled = true

        barChart.marker = mv
    }
}