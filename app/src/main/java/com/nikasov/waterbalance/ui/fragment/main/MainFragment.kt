package com.nikasov.waterbalance.ui.fragment.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nikasov.waterbalance.R
import com.nikasov.waterbalance.data.intake.WaterIntake
import com.nikasov.waterbalance.ui.adapter.WaterIntakeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel : MainViewModel by viewModels()

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
        val animator = ObjectAnimator.ofInt(circularProgressBar, "progress", 0, viewModel.goal)
        animator.interpolator = DecelerateInterpolator()
        animator.duration = 500
        animator.start()
    }

    private fun initProgress() {
        viewModel.goal.also { goal ->
            circularProgressBar.progressMax = goal.toFloat()
            val goalTxt = "$goal ml"
            goalValue.text = goalTxt
        }
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

        intakeValue.text = "$progress"

        circularProgressBar.progress =
            if (progress >= viewModel.goal) {
                circularProgressBar.progressMax
            } else {
                progress.toFloat()
            }


        val howMuchStr =
            if (viewModel.goal - progress <= 0) {
                "Well done!"
            } else {
                "${viewModel.goal - progress}"
            }

        howMuchValue.text = howMuchStr
    }

    private fun initWaterDialog() {
        waterDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.amount_of_water))
            .setItems(resources.getStringArray(R.array.amounts_of_water)) { dialog, which ->
                when (which) {
                    1 -> Timber.d(which.toString())
                }
            }
    }

    private fun showWaterDialog() {
        waterDialog.show()
    }
}