package com.nikasov.waterbalance.ui.fragment.onboarding.summary

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikasov.waterbalance.data.Prefs
import kotlinx.coroutines.launch

class SettingsOnboardingViewModel @ViewModelInject constructor(
    private val prefs: Prefs
) : ViewModel() {

    val goal = prefs.loadGoal()
}