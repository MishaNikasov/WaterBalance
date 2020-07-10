package com.nikasov.waterbalance.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nikasov.waterbalance.common.Constants
import com.nikasov.waterbalance.data.day.Day
import com.nikasov.waterbalance.data.day.DayDAO

@Database(entities = [Day::class], version = Constants.DATABASE_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun getDayDAO() : DayDAO
}