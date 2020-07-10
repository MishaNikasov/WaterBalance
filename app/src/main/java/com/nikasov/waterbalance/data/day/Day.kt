package com.nikasov.waterbalance.data.day

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikasov.waterbalance.common.Constants
import java.util.*

@Entity(tableName = Constants.TABLE)
data class Day (
    var firstStoryText : String,
    var secondStoryText : String,
    var date : Date
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
}