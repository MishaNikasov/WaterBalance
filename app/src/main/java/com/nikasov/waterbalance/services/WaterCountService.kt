package com.nikasov.waterbalance.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.nikasov.waterbalance.common.Constants.ACTION_START_OR_RESUME_SERVICE
import com.nikasov.waterbalance.common.Constants.ACTION_PAUSE_SERVICE
import com.nikasov.waterbalance.common.Constants.ACTION_STOP_SERVICE
import com.nikasov.waterbalance.common.Constants.NOTIFICATION_CHANNEL_ID
import com.nikasov.waterbalance.common.Constants.NOTIFICATION_CHANNEL_NAME
import com.nikasov.waterbalance.common.Constants.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class WaterCountService : LifecycleService() {

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder

    companion object {
        val curAmount = MutableLiveData<String>()
        val maxAmount = MutableLiveData<String>()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var isFirstRun = true

        intent?.let {
            when(it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        Timber.d("ACTION_START")
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        Timber.d("ACTION_RESUME")
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    Timber.d("PAUSE_SERVICE")
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("STOP_SERVICE")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }



    private fun startForegroundService() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        startForeground(NOTIFICATION_ID, baseNotificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}