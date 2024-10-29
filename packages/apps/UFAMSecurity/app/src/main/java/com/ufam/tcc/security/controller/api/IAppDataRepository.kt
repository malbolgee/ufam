package com.ufam.tcc.security.controller.api

interface IAppDataRepository {
    suspend fun closeAllBut(doors: IntArray)
    suspend fun getAllOpenDoors(): IntArray
}