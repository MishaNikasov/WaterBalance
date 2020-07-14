package com.nikasov.waterbalance.di

import android.content.Context
import androidx.room.Room
import com.nikasov.waterbalance.common.Constants
import com.nikasov.waterbalance.data.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideHistoryDatabase (
        @ApplicationContext app : Context
    ) = Room.databaseBuilder(
        app,
        Database::class.java,
        Constants.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun getWaterIntakesDAO (db : Database) = db.getWaterIntakesDAO()

}