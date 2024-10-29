package com.ufam.tcc.security.controller.datasource

import com.ufam.tcc.security.controller.backend.Backend
import javax.inject.Inject

class AppLocalDataSource @Inject constructor(
    private val backEnd: Backend,
) {

    fun closeAllBut(doors: IntArray) {
        backEnd.closeAllBut(doors)
    }

    fun getAllOpenDoors() = backEnd.getAllOpenDoors()
}