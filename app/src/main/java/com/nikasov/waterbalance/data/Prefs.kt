package com.nikasov.waterbalance.data

import android.content.Context
import androidx.preference.PreferenceManager
import com.nikasov.waterbalance.common.Constants
import com.nikasov.waterbalance.utils.booleanLiveData
import com.nikasov.waterbalance.utils.intLiveData
import com.nikasov.waterbalance.utils.stringLiveData
import javax.inject.Inject

class Prefs @Inject constructor (
    context: Context
) {

    companion object {
        const val CURRENT_WATER_INTAKE = "current_water_intake"
        const val WATER_GOAL = "water_goal"
        const val SEX = "sex"
        const val WEIGHT = "weight"
        const val IS_ONBOARDING_DONE = "is_onboarding_done"
        const val AUTO_CALCULATION = "auto_calculation"
        const val FIRST_RUN = "first_run"
    }

    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = sharedPref.edit()

    fun loadCurrentWaterIntake() = sharedPref.intLiveData(CURRENT_WATER_INTAKE, 300)

    fun loadGoal() = sharedPref.stringLiveData(WATER_GOAL, "2500")

    fun loadSex() = sharedPref.stringLiveData(SEX, "male")

    fun loadWeight() = sharedPref.stringLiveData(WEIGHT, "60")

    fun isOnboardingDone() = sharedPref.booleanLiveData(IS_ONBOARDING_DONE, false)

    fun isAutoCalculation() = sharedPref.booleanLiveData(AUTO_CALCULATION, false)

    fun isFirstRun() = sharedPref.booleanLiveData(FIRST_RUN, true)

    fun saveCurrentWaterIntakeAmount (amount: Int) {
        editor.apply {
            putInt(CURRENT_WATER_INTAKE, amount)
        }.apply()
    }

    fun saveSex (sex: String) {
        editor.apply {
            putString(SEX, sex)
        }.apply()
    }

    fun saveWeight (weight: String) {
        editor.apply {
            putString(WEIGHT, weight)
        }.apply()
    }

    private fun saveGoal (goal: String) {
        editor.apply {
            putString(WATER_GOAL, goal)
        }.apply()
    }

    fun saveIsOnboardingDone() {
        editor.apply {
            putBoolean(IS_ONBOARDING_DONE, true)
        }.apply()
    }

    fun saveIsFirstRun() {
        editor.apply {
            putBoolean(FIRST_RUN, false)
        }.apply()
    }

    fun setGoal(amount : String? = null) {
        if (!isAutoCalculation().value!!) {
            calculateWaterAmount()
        } else {
            saveGoal(sharedPref.getString(WATER_GOAL, amount)!!)
        }
    }

    private fun calculateWaterAmount() {

        val sex = loadSex().value
        val weight = loadWeight().value

        val goal = if (sex!! == "male") {
            (35f*weight!!.toFloat()).toInt()
        } else {
            (31f*weight!!.toFloat()).toInt()
        }

        saveGoal("$goal")
    }
}