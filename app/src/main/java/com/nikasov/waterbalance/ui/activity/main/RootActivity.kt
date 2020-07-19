package com.nikasov.waterbalance.ui.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import com.nikasov.waterbalance.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_root.*

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {

    private val viewModel : RootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        initNavController()
        checkIfOnboardingDone()
    }

    private fun initNavController() {
        hostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.onboardingFragment -> {
                }
                else -> {
                }
            }
        }
    }

    private fun checkIfOnboardingDone() {
        if (!viewModel.isOnboardingDone()) {
            hostFragment.findNavController().apply {
                popBackStack()
                navigate(R.id.to_onboardingFragment)
            }
        }
    }
}