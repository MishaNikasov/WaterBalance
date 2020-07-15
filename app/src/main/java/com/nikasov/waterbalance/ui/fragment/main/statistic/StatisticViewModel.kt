package com.nikasov.waterbalance.ui.fragment.main.statistic

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nikasov.waterbalance.data.intake.WaterIntake
import com.nikasov.waterbalance.data.repository.WaterIntakesRepository

class StatisticViewModel @ViewModelInject constructor(
    private val waterIntakesRepository: WaterIntakesRepository
) : ViewModel() {

    val waterIntakes: LiveData<List<WaterIntake>> = waterIntakesRepository.getAllWaterIntakes()

    fun getAllWaterIntakesAmount(list : List<WaterIntake>) : Int {
        var intakesAmount = 0
        list.forEach { intake ->
            intakesAmount += intake.amount
        }
        return intakesAmount
    }

}