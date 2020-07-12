package com.nikasov.waterbalance.ui.fragment.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikasov.waterbalance.common.Prefs
import com.nikasov.waterbalance.data.intake.WaterIntake
import com.nikasov.waterbalance.data.repository.WaterIntakesRepository
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel @ViewModelInject constructor (
    private val waterIntakesRepository: WaterIntakesRepository,
    private val prefs: Prefs
) : ViewModel() {

    private val currentDate = Calendar.getInstance().time
    val goal = prefs.loadGoal()

    val waterIntakes: LiveData<List<WaterIntake>> = waterIntakesRepository.getAllWaterIntakes()

    fun addWaterIntake() {
        viewModelScope.launch {
            waterIntakesRepository.insertWaterIntake(
                WaterIntake(
                    currentDate,
                    50
                )
            )
        }
    }
}