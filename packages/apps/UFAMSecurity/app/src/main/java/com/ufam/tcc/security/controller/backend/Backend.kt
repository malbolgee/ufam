package com.ufam.tcc.security.controller.backend

import android.content.Context
import android.util.Log
import com.ufam.android.security.UfamSecurity

class Backend(private val context: Context) {

    companion object {
        val LOG_TAG = Backend::class.simpleName!!
    }

    private val ufamSecuritySdk = try {
        UfamSecurity(context)
    } catch (e: Throwable) {
        Log.d(LOG_TAG, "Could not create UFAM Security instance: $e")
        null
    }

    fun closeAllBut(doors: IntArray) {
        ufamSecuritySdk?.closeAllBut(doors)
    }

    fun getAllOpenDoors(): IntArray =
        ufamSecuritySdk?.allOpenDoors ?: intArrayOf()
}