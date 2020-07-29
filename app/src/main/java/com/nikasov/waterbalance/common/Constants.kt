package com.nikasov.waterbalance.common

object Constants {

    // database
    const val DATABASE_VERSION = 3
    const val DATABASE_NAME = "db"
    const val WATER_INTAKE_TABLE = "WATER_INTAKE_TABLE"

    // service
    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_WATER_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_OPEN_FRAGMENT = "ACTION_OPEN_FRAGMENT"

    // notification
    const val NOTIFICATION_CHANNEL_ID = "water_counter_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Water Counter"
    const val NOTIFICATION_ID = 1

}