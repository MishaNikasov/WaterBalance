package com.nikasov.waterbalance.data.intake

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface WaterIntakeDAO {
    @Insert
    suspend fun insertWaterIntake (waterIntake: WaterIntake)
    @Delete
    suspend fun deleteWaterIntake (waterIntake: WaterIntake)
    @Query("SELECT * FROM water_intake_table WHERE day = :date ORDER BY TIME")
    fun getWaterIntakesByDate(date : Date) : LiveData<List<WaterIntake>>
    @Query("SELECT * FROM water_intake_table WHERE day = :date ORDER BY TIME")
    suspend fun getWaterIntakesListByDate(date : Date) : List<WaterIntake>
    @Query("SELECT * FROM water_intake_table ORDER BY TIME")
    fun getAllWaterIntakes() : LiveData<List<WaterIntake>>
}