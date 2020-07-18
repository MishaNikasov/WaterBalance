package com.nikasov.waterbalance.di

import android.content.Context
import com.nikasov.waterbalance.data.Prefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun providePrefs(@ApplicationContext context: Context) : Prefs =
        Prefs(context)
}