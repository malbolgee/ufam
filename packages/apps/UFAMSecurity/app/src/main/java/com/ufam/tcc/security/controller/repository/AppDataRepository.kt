package com.ufam.tcc.security.controller.repository

import com.ufam.tcc.security.controller.api.IAppDataRepository
import com.ufam.tcc.security.controller.datasource.AppLocalDataSource
import com.ufam.tcc.security.controller.di.Dispatcher
import com.ufam.tcc.security.controller.di.UFAMDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDataRepository @Inject constructor(
    private val appLocalDataSource: AppLocalDataSource,
    @Dispatcher(UFAMDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : IAppDataRepository {

    override suspend fun closeAllBut(doors: IntArray) {
        withContext(ioDispatcher) {
            appLocalDataSource.closeAllBut(doors)
        }
    }

    override suspend fun getAllOpenDoors(): IntArray = appLocalDataSource.getAllOpenDoors()

}