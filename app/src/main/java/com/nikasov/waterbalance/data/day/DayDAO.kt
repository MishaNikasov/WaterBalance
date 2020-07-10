package com.nikasov.waterbalance.data.day

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface DayDAO {

    @Insert
    suspend fun insertDay (day : Day)

}