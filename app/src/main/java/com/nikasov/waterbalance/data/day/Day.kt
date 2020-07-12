package com.nikasov.waterbalance.data.day

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikasov.waterbalance.common.Constants
import com.nikasov.waterbalance.data.intake.WaterIntake
import java.util.*

@Entity(tableName = Constants.DAY_TABLE)
data class Day (
//    var listOfWaterIntakes : List<WaterIntake>,
    var date : Date
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
}