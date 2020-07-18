package com.nikasov.waterbalance.ui.fragment.main.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nikasov.waterbalance.data.Prefs

class SettingsViewModel @ViewModelInject constructor(
    private val prefs: Prefs
) : ViewModel() {

    val weight = prefs.loadWeight()
    val sex = prefs.loadSex()
    val isAutoCalculation = prefs.isAutoCalculation()
    val goal = prefs.loadGoal()

    fun setUpGoal(amount : String? = null) {
        prefs.setGoal(amount)
    }
}