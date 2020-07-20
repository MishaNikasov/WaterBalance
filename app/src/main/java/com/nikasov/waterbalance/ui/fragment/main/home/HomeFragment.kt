package com.nikasov.waterbalance.ui.fragment.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.elconfidencial.bubbleshowcase.BubbleShowCase
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder
import com.elconfidencial.bubbleshowcase.BubbleShowCaseListener
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence
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
        isFirstRun()
        initUi()
    }

    private fun isFirstRun() {
        if (viewModel.isFirstRun().value!!) {
            viewModel.saveIsFirstRun()
            showCase()
        }
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

    private fun initProgress() {

        viewModel.goal.observe(viewLifecycleOwner, Observer {
            it.also { goal ->
                circularProgressBar.progressMax = goal.toFloat()
                val goalTxt = "$goal ml"
                goalValue.text = goalTxt
            }
            updateUi()
        })

        viewModel.currentWaterIntakeAmount.observe(viewLifecycleOwner, Observer { amount ->
            val amountTxt = "$amount ml"
            nextAmount.text = amountTxt
        })
    }

    private fun initWaterIntakesList() {
        val waterIntakeListener = object : WaterIntakeAdapter.Interaction {
            override fun onItemSelected(position: Int, item: WaterIntake) {
                viewModel.deleteWaterIntake(item)
            }
        }

        val waterIntakeAdapter = WaterIntakeAdapter(waterIntakeListener)

        waterIntakesRecycler.apply {
            adapter = waterIntakeAdapter
        }
        viewModel.waterIntakes.observe(viewLifecycleOwner, Observer {list ->
            waterIntakeAdapter.submitList(list)
            viewModel.setCurrentProgress(list)
            updateUi()
        })
    }

    private fun updateUi() {
        val intake = "${viewModel.currentProgress} ml"
        intakeValue.text = intake

        circularProgressBar.setProgressWithAnimation(viewModel.currentProgress.toFloat(), 1000)

        val howMuchStr =
            if (viewModel.goal.value!!.toInt() - viewModel.currentProgress <= 0) {
                "Well done!"
            } else {
                "${viewModel.goal.value!!.toInt() - viewModel.currentProgress} ml"
            }

        val currentAmountPercent = "${((viewModel.currentProgress.toFloat()/viewModel.goal.value!!.toFloat())*100).toInt()}%"

        percentTxt.text = currentAmountPercent
        howMuchValue.text = howMuchStr
    }

    private fun initWaterDialog() {
        waterDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.amount_of_water))
            .setItems(resources.getStringArray(R.array.amounts_of_water)) { _, which ->
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

    private fun addWaterIntake() {
        viewModel.addWaterIntake()
    }

    private fun showCase() {

        val showCaseDismiss = object : BubbleShowCaseListener {
            override fun onTargetClick(bubbleShowCase: BubbleShowCase) {
                bubbleShowCase.dismiss()
            }
            override fun onCloseActionImageClick(bubbleShowCase: BubbleShowCase) {
                bubbleShowCase.dismiss()
            }
            override fun onBubbleClick(bubbleShowCase: BubbleShowCase) {
                bubbleShowCase.dismiss()
            }
            override fun onBackgroundDimClick(bubbleShowCase: BubbleShowCase) {
                bubbleShowCase.dismiss()
            }
        }

        val amountShowcase = BubbleShowCaseBuilder(requireActivity())
            .title("foo")
            .targetView(floatingChangeButton)
            .backgroundColor(resources.getColor(R.color.colorPrimary))
            .disableCloseAction(true)
            .description("bar")
            .listener(showCaseDismiss)
            .arrowPosition(BubbleShowCase.ArrowPosition.TOP)

        val addShowcase = BubbleShowCaseBuilder(requireActivity())
            .title("foo")
            .disableCloseAction(true)
            .targetView(floatingAddButton)
            .backgroundColor(resources.getColor(R.color.colorPrimary))
            .description("bar")
            .listener(showCaseDismiss)
            .arrowPosition(BubbleShowCase.ArrowPosition.TOP)

        BubbleShowCaseSequence()
            .addShowCase(amountShowcase)
            .addShowCase(addShowcase)
            .show()
    }

}