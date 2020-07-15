package com.nikasov.waterbalance.ui.fragment.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nikasov.waterbalance.R
import com.nikasov.waterbalance.data.intake.WaterIntake
import com.nikasov.waterbalance.ui.adapter.WaterIntakeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel : HomeViewModel by viewModels()

    private lateinit var waterDialog : MaterialAlertDialogBuilder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {

        initProgress()
        initWaterIntakesList()
        initWaterDialog()

        floatingAddButton.setOnClickListener {
            addWaterIntake()
        }

        floatingChangeButton.setOnClickListener {
            showWaterDialog()
        }
    }

    private fun addWaterIntake() {
        viewModel.addWaterIntake()
    }

    private fun initProgress() {
        viewModel.goal.also { goal ->
            circularProgressBar.progressMax = goal.toFloat()
            val goalTxt = "$goal ml"
            goalValue.text = goalTxt
        }
        viewModel.currentWaterIntakeAmount.observe(viewLifecycleOwner, Observer { amount ->
            val amountTxt = "$amount ml"
            nextAmount.text = amountTxt
        })
    }

    private fun initWaterIntakesList() {
        val waterIntakeAdapter = WaterIntakeAdapter()
        waterIntakesRecycler.apply {
            adapter = waterIntakeAdapter
        }
        viewModel.waterIntakes.observe(viewLifecycleOwner, Observer {list ->
            waterIntakeAdapter.submitList(list)
            updateProgress(list)
        })
    }

    private fun updateProgress(list : List<WaterIntake>) {

        var progress = 0
        list.forEach { waterIntake ->
            progress += waterIntake.amount
        }
        val intake = "$progress ml"
        intakeValue.text = intake

        if (progress >= viewModel.goal) {
            circularProgressBar.progress = circularProgressBar.progressMax
        } else {
            circularProgressBar.setProgressWithAnimation(progress.toFloat(), 1000)
        }

        val howMuchStr =
            if (viewModel.goal - progress <= 0) {
                "Well done!"
            } else {
                "${viewModel.goal - progress} ml"
            }

        val currentAmountPercent = "${((progress.toFloat()/viewModel.goal.toFloat())*100).toInt()}%"

        percentTxt.text = currentAmountPercent
        howMuchValue.text = howMuchStr
    }

    private fun initWaterDialog() {
        waterDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.amount_of_water))
            .setItems(resources.getStringArray(R.array.amounts_of_water)) { dialog, which ->
                when (which) {
                    0 -> viewModel.saveCurrentIntake(300)
                    1 -> viewModel.saveCurrentIntake(400)
                    2 -> viewModel.saveCurrentIntake(500)
                    3 -> viewModel.saveCurrentIntake(600)
                }
            }
    }

    private fun showWaterDialog() {
        waterDialog.show()
    }
}