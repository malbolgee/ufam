package com.ufam.tcc.security.controller

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.ufam.android.security.UfamSecurity
import com.ufam.tcc.security.controller.ui.navigation.Navigation
import com.ufam.tcc.security.controller.ui.screen.mainscreen.viewmodel.MainScreenViewModel
import com.ufam.tcc.security.controller.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainScreenViewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Navigation(
                    mainScreenViewModel = mainScreenViewModel
                )
            }
        }

//        GlobalScope.launch(Dispatchers.IO) {
//            callSdk(applicationContext)
//        }

    }
}

fun callSdk(context: Context) {
    UfamSecurity(context).closeAllBut(intArrayOf())
}
