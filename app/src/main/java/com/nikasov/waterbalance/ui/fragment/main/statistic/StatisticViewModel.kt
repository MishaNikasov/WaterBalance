package com.nikasov.waterbalance.ui.fragment.main.statistic

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikasov.waterbalance.data.intake.WaterIntake
import com.nikasov.waterbalance.data.repository.WaterIntakesRepository
import com.nikasov.waterbalance.utils.DateUtils
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

typealias WaterIntakesMap = HashMap<Date, List<WaterIntake>>

class StatisticViewModel @ViewModelInject constructor(
    private val waterIntakesRepository: WaterIntakesRepository
) : ViewModel() {

    val waterIntakes: LiveData<List<WaterIntake>> = waterIntakesRepository.getAllWaterIntakes()

    val waterIntakesMapByDay: MutableLiveData<WaterIntakesMap> = MutableLiveData()

    fun getAllWaterIntakesAmount(list : List<WaterIntake>) : Int {
        var intakesAmount = 0
        list.forEach { intake ->
            intakesAmount += intake.amount
        }
        return intakesAmount
    }

    private fun getDaysList() : List<Date> {
        val listOfDays = arrayListOf<Date>()

        val cal = Calendar.getInstance()

        cal.time = DateUtils.getDayByDate(cal.time)

        for (i in 1..7) {
            cal[Calendar.DAY_OF_WEEK] = i
            listOfDays.apply {
                add(cal.time)
            }
        }

        return listOfDays
    }

    fun getStatByDays() {
        viewModelScope.launch {
            val listOfWaterIntakesByDay : WaterIntakesMap = hashMapOf()
            getDaysList().forEach { date ->
                val list = waterIntakesRepository.getWaterIntakesListByDAte(date)
                listOfWaterIntakesByDay[date] = list
            }
            waterIntakesMapByDay.postValue(listOfWaterIntakesByDay)
        }
    }

}