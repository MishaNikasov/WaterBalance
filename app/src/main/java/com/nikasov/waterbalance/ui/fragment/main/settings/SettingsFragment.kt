package com.nikasov.waterbalance.ui.fragment.main.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceFragmentCompat
import com.nikasov.waterbalance.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel : SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSettings()
    }

    private fun initSettings() {
        viewModel.sex.observe(viewLifecycleOwner, Observer {
            viewModel.setUpGoal()
        })
        viewModel.weight.observe(viewLifecycleOwner, Observer {
            viewModel.setUpGoal()
        })
        viewModel.isAutoCalculation.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.setUpGoal()
            } else
                viewModel.setUpGoal()
        })
        viewModel.goal.observe(viewLifecycleOwner, Observer {
            viewModel.setUpGoal()
        })
    }
}