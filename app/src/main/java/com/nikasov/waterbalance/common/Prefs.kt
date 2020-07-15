package com.nikasov.waterbalance.common

import android.content.Context
import androidx.preference.PreferenceManager
import javax.inject.Inject

class Prefs @Inject constructor (
    context: Context
) {
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = sharedPref.edit()

    fun loadGoal() : Int {
        return if (!isAutoCalculation()) {
            calculateWaterAmount()
        } else {
            sharedPref.getString(Constants.WATER_GOAL, "2500")!!.toInt()
        }
    }

    fun loadCurrentWaterIntake() : Int = sharedPref.getInt(Constants.CURRENT_WATER_INTAKE, 200)

    fun loadSex() : String? = sharedPref.getString("sex", "male")

    fun loadWeight() : String? = sharedPref.getString("weight", "60")

    fun isOnboardingDone() : Boolean = sharedPref.getBoolean(Constants.IS_ONBOARDING_DONE, false)

    fun isAutoCalculation() : Boolean = sharedPref.getBoolean("auto_calculation", false)

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

    fun saveIsOnboardingDone() {
        editor.apply {
            putBoolean(Constants.IS_ONBOARDING_DONE, true)
        }.apply()
    }

    private fun calculateWaterAmount() : Int {

        val sex = loadSex()
        val weight = loadWeight()

        return if (sex!! == "male") {
            (35f*weight!!.toFloat()).toInt()
        } else {
            (31f*weight!!.toFloat()).toInt()
        }
    }
}