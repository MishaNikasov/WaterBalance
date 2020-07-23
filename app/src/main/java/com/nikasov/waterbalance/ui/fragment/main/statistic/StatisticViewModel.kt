package com.nikasov.waterbalance.ui.fragment.main.statistic

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarEntry
import com.nikasov.waterbalance.data.intake.WaterIntake
import com.nikasov.waterbalance.data.repository.WaterIntakesRepository
import com.nikasov.waterbalance.utils.DateUtils
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

typealias WaterIntakesMap = Map<Date, List<WaterIntake>>

class StatisticViewModel @ViewModelInject constructor(
    private val waterIntakesRepository: WaterIntakesRepository
) : ViewModel() {

    val waterIntakes: LiveData<List<WaterIntake>> = waterIntakesRepository.getAllWaterIntakes()

    val waterIntakesMapByWeek: MutableLiveData<Map<Date, String>> = MutableLiveData()
    val waterIntakesMapByMonth: MutableLiveData<Map<Date, String>> = MutableLiveData()

    //days stat
    var weekDateRange = ""
    var weekDaysString = arrayListOf<String>()
    var weekEntryList = arrayListOf<BarEntry>()

    //month stat
    var monthText = ""
    var monthDaysString = arrayListOf<String>()
    var monthEntryList = arrayListOf<BarEntry>()

    //all stats
    var allWaterIntakesAmount = ""
    var avgWaterIntakesAmountByDay = ""
    var avgWaterIntakesCountByDay =  ""

    enum class StatState {
        WEEK,
        MONTH
    }

    private fun getAllStat(list : WaterIntakesMap) {

        var intakesAmount = 0
        var allIntakesCount =  0
        var allDaysCount = 0

        list.values.onEach { intakeList ->
            if (intakeList.isNotEmpty()) {

                allDaysCount++

                intakeList.onEach { intake ->
                    allIntakesCount++
                    intakesAmount += intake.amount
                }
            }
        }

        val avgWaterIntakesAmount = intakesAmount/allDaysCount
        val avgWaterIntakesCount = allIntakesCount/allDaysCount

        allWaterIntakesAmount = "${intakesAmount/1000f} l"
        avgWaterIntakesAmountByDay = "${avgWaterIntakesAmount/1000f} l"
        avgWaterIntakesCountByDay = "$avgWaterIntakesCount"

    }

    fun getStatByDays(state: StatState) {
        viewModelScope.launch {

            val daysList = getDaysList(state)
            val mapOfWaterIntakesByDay = hashMapOf<Date, String>()
            val mapOfWaterIntakes = hashMapOf<Date, List<WaterIntake>>()

            daysList.forEach { date ->
                var sum = 0
                val waterIntakeList : ArrayList<WaterIntake> = arrayListOf()
                waterIntakesRepository.getWaterIntakesListByDate(date).forEach {
                    sum += it.amount
                    waterIntakeList.add(it)
                }
                mapOfWaterIntakes[date] = waterIntakeList
                mapOfWaterIntakesByDay[date] = "$sum"
            }

            val result = mapOfWaterIntakesByDay.toList().sortedBy {
                    (key, _) -> key
            }.toMap()

            setStatTitle(result.keys.toList(), state)
            setEntryList(result.values.toList(), state)

            getAllStat(mapOfWaterIntakes)

            if (state == StatState.WEEK) {
                waterIntakesMapByWeek.postValue(result)
            }  else if (state == StatState.MONTH) {
                waterIntakesMapByMonth.postValue(result)
            }
        }
    }

    private fun getDaysList(state: StatState) : List<Date> {

        val listOfDays = arrayListOf<Date>()

        val cal = Calendar.getInstance()
        cal.time = DateUtils.getDayByDate(cal.time)

        val daysStringArray = arrayListOf<String>()

        if (state == StatState.WEEK) {
            var count = cal.firstDayOfWeek

            for (i in 1..7) {
                cal[Calendar.DAY_OF_WEEK] = count
                listOfDays.apply {
                    add(cal.time)
                }
                daysStringArray.add(DateUtils.getDay(cal.time))
                count++
            }

            weekDaysString = daysStringArray

        } else if (state == StatState.MONTH) {
            var count = 1

            for (i in 1..cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                cal[Calendar.DAY_OF_MONTH] = count
                listOfDays.apply {
                    add(cal.time)
                }
                daysStringArray.add(DateUtils.getFormattedDay(cal.time))
                count++
            }

            monthDaysString = daysStringArray
        }
        return listOfDays
    }

    private fun setEntryList(valuesList: List<String>, state: StatState) {

        val list = arrayListOf<BarEntry>()
        var i = 0f

        valuesList.forEach {
            list.add(BarEntry(i, it.toFloat()))
            i++
        }

        if (state == StatState.WEEK) {
            weekEntryList.clear()
            weekEntryList = list
        } else if (state == StatState.MONTH) {
            monthEntryList.clear()
            monthEntryList = list
        }
    }

    private fun setStatTitle(list: List<Date>, state: StatState) {

        val statTitle = StringBuilder()

        if (state == StatState.WEEK) {
            statTitle.append(DateUtils.getFormattedDay(list[0]))
            statTitle.append(" - ")
            statTitle.append(DateUtils.getFormattedDay(list[list.size - 1]))
            weekDateRange = statTitle.toString()
        } else if (state == StatState.MONTH) {
            statTitle.append(DateUtils.getMonth(list[0]))
            monthText = statTitle.toString()
        }
    }

}