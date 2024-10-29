package com.ufam.tcc.security.controller.di

import android.content.Context
import com.ufam.tcc.security.controller.backend.Backend
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BackendModule {

    @Provides
    @Singleton
    fun providesBackend(
        @ApplicationContext context: Context,
    ): Backend = Backend(context)
}
