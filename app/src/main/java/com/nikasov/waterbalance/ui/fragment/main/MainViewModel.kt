package com.nikasov.waterbalance.ui.fragment.main

import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nikasov.waterbalance.data.Prefs
import com.nikasov.waterbalance.ui.fragment.main.home.HomeFragment
import com.nikasov.waterbalance.ui.fragment.main.settings.SettingsFragment
import com.nikasov.waterbalance.ui.fragment.main.statistic.StatisticFragment

class MainViewModel @ViewModelInject constructor(
    private val prefs: Prefs
) : ViewModel() {

    fun getOnboardingFragmentsList() : List<Fragment> {
        return arrayListOf(
            HomeFragment(),
            StatisticFragment(),
            SettingsFragment()
        )
    }
}