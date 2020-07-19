package com.nikasov.waterbalance.ui.fragment.main.statistic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nikasov.waterbalance.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistic.*
import timber.log.Timber

@AndroidEntryPoint
class StatisticFragment : Fragment(R.layout.fragment_statistic) {

    private val viewModel : StatisticViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStat()
    }

    //TODO: чарт

    private fun initStat() {

        viewModel.waterIntakes.observe(viewLifecycleOwner, Observer { list ->
            viewModel.getStatByDays()
        })

        viewModel.waterIntakesMapByDay.observe(viewLifecycleOwner, Observer { list ->
            list.forEach{ waterIntakesMap ->
                val t = "${txt.text}\n${waterIntakesMap.key}, ${waterIntakesMap.value.size}"
                txt.text = t
            }
        })
    }
}