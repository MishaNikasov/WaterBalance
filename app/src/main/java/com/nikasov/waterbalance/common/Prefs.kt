package com.nikasov.waterbalance.common

import android.content.Context
import javax.inject.Inject

class Prefs @Inject constructor (
    context: Context
) {
    private val sharedPref = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    fun loadGoal() : Int {
        return sharedPref.getInt(Constants.WATER_GOAL, 2500)
    }

    fun saveWaterGoal(goal: Int) {
        editor.apply {
            putInt(Constants.WATER_GOAL, goal)
        }.apply()
    }

    fun loadCurrentWaterIntake() : Int {
        return sharedPref.getInt(Constants.CURRENT_WATER_INTAKE, 100)
    }

    fun saveCurrentWaterIntakeAmount (amount: Int) {
        editor.apply {
            putInt(Constants.CURRENT_WATER_INTAKE, amount)
        }.apply()
    }
}