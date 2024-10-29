package com.ufam.tcc.security.controller.di

import com.ufam.tcc.security.controller.api.IAppDataRepository
import com.ufam.tcc.security.controller.repository.AppDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsAppDataRepository(appDataRepository: AppDataRepository): IAppDataRepository

}