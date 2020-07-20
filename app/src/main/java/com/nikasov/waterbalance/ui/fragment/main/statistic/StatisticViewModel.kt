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
import timber.log.Timber
import java.lang.StringBuilder
import java.util.*

typealias WaterIntakesMap = Map<Date, String>

class StatisticViewModel @ViewModelInject constructor(
    private val waterIntakesRepository: WaterIntakesRepository
) : ViewModel() {

    val waterIntakes: LiveData<List<WaterIntake>> = waterIntakesRepository.getAllWaterIntakes()

    val waterIntakesMapByDay: MutableLiveData<WaterIntakesMap> = MutableLiveData()

    var dateRange = ""
    var daysString = arrayListOf<String>()
    var entryList = arrayListOf<BarEntry>()

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

        val daysStringArray = arrayListOf<String>()

        var count : Int = cal.firstDayOfWeek

        for (i in 1..7) {
            cal[Calendar.DAY_OF_WEEK] = count
            listOfDays.apply {
                add(cal.time)
            }
            daysStringArray.add(DateUtils.getDay(cal.time))
            count++
        }

        daysString = daysStringArray

        return listOfDays
    }

    fun getStatByDays() {
        viewModelScope.launch {
            val listOfWaterIntakesByDay = hashMapOf<Date, String>()
            getDaysList().forEach { date ->
                var sum = 0
                waterIntakesRepository.getWaterIntakesListByDate(date).forEach {
                    sum += it.amount
                }
                listOfWaterIntakesByDay[date] = "$sum"
            }
            val result = listOfWaterIntakesByDay.toList().sortedBy {
                    (key, _) -> key
            }.toMap()

            setDateRange(result.keys.toList())
            setEntryList(result.values.toList())

            waterIntakesMapByDay.postValue(result)
        }
    }

    private fun setEntryList(valuesList: List<String>) {
        entryList.clear()
        val list = arrayListOf<BarEntry>()
        var i = 0f
        valuesList.forEach {
            list.add(BarEntry(i, it.toFloat()))
            i++
        }
        entryList = list
    }

    private fun setDateRange(list: List<Date>) {
        val dateRangeStr = StringBuilder()
        dateRangeStr.append(DateUtils.getFormattedDay(list[0]))
        dateRangeStr.append(" - ")
        dateRangeStr.append(DateUtils.getFormattedDay(list[list.size - 1]))
        dateRange = dateRangeStr.toString()
    }

}