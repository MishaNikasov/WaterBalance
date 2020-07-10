package com.nikasov.waterbalance.ui.fragment.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nikasov.waterbalance.data.repository.DayRepository

class MainViewModel @ViewModelInject constructor(
    private val dayRepository: DayRepository
) : ViewModel() {



}