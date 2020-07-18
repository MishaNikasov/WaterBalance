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
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = sharedPref.edit()

    fun loadCurrentWaterIntake() = sharedPref.intLiveData(Constants.CURRENT_WATER_INTAKE, 200)

    fun loadGoal() = sharedPref.stringLiveData(Constants.WATER_GOAL, "2500")

    fun loadSex() = sharedPref.stringLiveData("sex", "male")

    fun loadWeight() = sharedPref.stringLiveData("weight", "60")

    fun isOnboardingDone() = sharedPref.booleanLiveData(Constants.IS_ONBOARDING_DONE, false)

    fun isAutoCalculation() = sharedPref.booleanLiveData("auto_calculation", false)

    fun saveCurrentWaterIntakeAmount (amount: Int) {
        editor.apply {
            putInt(Constants.CURRENT_WATER_INTAKE, amount)
        }.apply()
    }

    fun saveSex (sex: String) {
        editor.apply {
            putString("sex", sex)
        }.apply()
    }

    fun saveWeight (weight: String) {
        editor.apply {
            putString("weight", weight)
        }.apply()
    }

    fun saveGoal (goal: String) {
        editor.apply {
            putString(Constants.WATER_GOAL, goal)
        }.apply()
    }

    fun saveIsOnboardingDone() {
        editor.apply {
            putBoolean(Constants.IS_ONBOARDING_DONE, true)
        }.apply()
    }

    fun setGoal(amount : String? = null) {
        if (!isAutoCalculation().value!!) {
            calculateWaterAmount()
        } else {
            saveGoal(sharedPref.getString(Constants.WATER_GOAL, amount)!!)
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