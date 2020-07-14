package com.nikasov.waterbalance.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nikasov.waterbalance.common.Constants
import com.nikasov.waterbalance.data.intake.WaterIntake
import com.nikasov.waterbalance.data.intake.WaterIntakeDAO

@Database(entities = [WaterIntake::class], version = Constants.DATABASE_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun getWaterIntakesDAO() : WaterIntakeDAO
}