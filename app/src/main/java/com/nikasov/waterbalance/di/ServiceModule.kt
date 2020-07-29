package com.nikasov.waterbalance.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.nikasov.waterbalance.R
import com.nikasov.waterbalance.common.Constants
import com.nikasov.waterbalance.ui.activity.main.RootActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

//    @ServiceScoped
//    @Provides
//    fun provideClient(
//        @ApplicationContext context: Context
//    ) : WaterCountService()

    @ServiceScoped
    @Provides
    fun providerPendingIntent(
        @ApplicationContext context: Context
    ) = PendingIntent.getActivity(
            context,
        0,
        Intent(context, RootActivity::class.java).also {
            it.action = Constants.ACTION_OPEN_FRAGMENT
        }, PendingIntent.FLAG_UPDATE_CURRENT
    )

    @ServiceScoped
    @Provides
    fun providerNotificationBuilder(
        @ApplicationContext context: Context,
        pendingIntent: PendingIntent
    ) = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
        .setAutoCancel(false)
        .setOngoing(true)
        .setSmallIcon(R.drawable.ic_water)
        .setContentTitle("Water Count")
        .setContentText("Content")
        .setContentIntent(pendingIntent)
}