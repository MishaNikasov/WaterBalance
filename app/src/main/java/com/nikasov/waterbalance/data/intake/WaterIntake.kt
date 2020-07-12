package com.nikasov.waterbalance.data.intake

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikasov.waterbalance.common.Constants
import java.util.*

@Entity(tableName = Constants.WATER_INTAKE_TABLE)
data class WaterIntake (
  var date: Date = Date(),
  var amount: Int = 0,
  var type: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
}