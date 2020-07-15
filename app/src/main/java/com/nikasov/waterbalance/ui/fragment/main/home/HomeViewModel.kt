package com.nikasov.waterbalance.ui.fragment.main.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikasov.waterbalance.common.Prefs
import com.nikasov.waterbalance.data.intake.WaterIntake
import com.nikasov.waterbalance.data.repository.WaterIntakesRepository
import com.nikasov.waterbalance.utils.DateUtils
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel @ViewModelInject constructor (
    private val waterIntakesRepository: WaterIntakesRepository,
    private val prefs: Prefs
) : ViewModel() {

    val goal = prefs.loadGoal()
    val currentWaterIntakeAmount = prefs.loadCurrentWaterIntake()

//    var currentDay : Date = Date()

    var currentProgress = 0

    val waterIntakes: LiveData<List<WaterIntake>> = waterIntakesRepository.getWaterIntakesByDAte(getCurrentDay())

    fun setCurrentProgress(list : List<WaterIntake>) {
        var progress = 0
        list.forEach { waterIntake ->
            progress += waterIntake.amount
        }
        currentProgress = progress
    }

    fun setDay(day: Date) {
//        currentDay
    }

    fun saveCurrentIntake(amount: Int) {
        prefs.saveCurrentWaterIntakeAmount(amount)
    }

    private fun getCurrentTime() : Date {
        return Calendar.getInstance().time
    }

    private fun getCurrentDay() : Date {
        return DateUtils.getDayByDate(Calendar.getInstance().time)
    }

    fun addWaterIntake() {
        viewModelScope.launch {
            waterIntakesRepository.insertWaterIntake(
                WaterIntake(
                    getCurrentDay(),
                    getCurrentTime(),
                    currentWaterIntakeAmount.value!!
                )
            )
        }
    }
}