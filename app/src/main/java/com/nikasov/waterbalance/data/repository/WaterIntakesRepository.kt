package com.nikasov.waterbalance.data.repository

import androidx.lifecycle.LiveData
import com.nikasov.waterbalance.data.intake.WaterIntake
import com.nikasov.waterbalance.data.intake.WaterIntakeDAO
import java.util.*
import javax.inject.Inject

class WaterIntakesRepository @Inject constructor (
    private val waterIntakeDAO: WaterIntakeDAO
) {
    suspend fun insertWaterIntake (waterIntake: WaterIntake){
        waterIntakeDAO.insertWaterIntake(waterIntake)
    }
    fun getWaterIntakesByDAte(date : Date) : LiveData<List<WaterIntake>> {
        return waterIntakeDAO.getWaterIntakesByDAte(date)
    }
    fun getAllWaterIntakes() : LiveData<List<WaterIntake>> {
        return waterIntakeDAO.getAllWaterIntakes()
    }
}
