package com.nikasov.waterbalance.ui.fragment.statistic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nikasov.waterbalance.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistic.*

@AndroidEntryPoint
class StatisticFragment : Fragment(R.layout.fragment_statistic) {

    private val viewModel : StatisticViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStat()
    }

    private fun initStat() {
        viewModel.waterIntakes.observe(viewLifecycleOwner, Observer { list ->
            val totalAmountStr = "${viewModel.getAllWaterIntakesAmount(list)} ml"
            totalAmount.text = totalAmountStr
        })
    }
}