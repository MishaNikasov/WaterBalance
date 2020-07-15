package com.nikasov.waterbalance.ui.fragment.main.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nikasov.waterbalance.common.Prefs

class SettingsViewModel @ViewModelInject constructor(
    private val prefs: Prefs
) : ViewModel() {

    val weight = prefs.loadWeight()
    val sex = prefs.loadSex()
    val isAutoCalculation = prefs.isAutoCalculation()

    fun setUpGoal() {
        prefs.setGoal()
    }
}