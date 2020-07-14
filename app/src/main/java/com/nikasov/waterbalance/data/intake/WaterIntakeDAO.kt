package com.nikasov.waterbalance.data.intake

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface WaterIntakeDAO {
    @Insert
    suspend fun insertWaterIntake (waterIntake: WaterIntake)
    @Query("SELECT * FROM water_intake_table WHERE day = :date ORDER BY TIME")
    fun getWaterIntakesByDAte(date : Date) : LiveData<List<WaterIntake>>
    @Query("SELECT * FROM water_intake_table ORDER BY TIME")
    fun getAllWaterIntakes() : LiveData<List<WaterIntake>>
}