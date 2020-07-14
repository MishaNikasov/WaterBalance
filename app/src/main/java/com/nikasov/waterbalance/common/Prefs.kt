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
        return if (!sharedPref.getBoolean("auto_calculation", false)) {
            calculateWaterAmount()
        } else {
            sharedPref.getString(Constants. WATER_GOAL, "2500")!!.toInt()
        }
    }

    fun loadCurrentWaterIntake() : Int {
        return sharedPref.getInt(Constants.CURRENT_WATER_INTAKE, 200)
    }

    fun saveCurrentWaterIntakeAmount (amount: Int) {
        editor.apply {
            putInt(Constants.CURRENT_WATER_INTAKE, amount)
        }.apply()
    }

    private fun calculateWaterAmount() : Int{
        val sex = sharedPref.getString("sex", "male")
        val weight = sharedPref.getString("weight", "60")

        return if (sex!! == "male") {
            (35f*weight!!.toFloat()).toInt()
        } else {
            (31f*weight!!.toFloat()).toInt()
        }
    }
}