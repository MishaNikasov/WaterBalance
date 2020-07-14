package com.nikasov.waterbalance.ui.activity.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nikasov.waterbalance.common.Prefs

class RootViewModel @ViewModelInject constructor(
    private val prefs: Prefs
) : ViewModel() {
    fun isOnboardingDone() : Boolean = prefs.isOnboardingDone()
}