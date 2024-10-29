package com.ufam.tcc.security.controller.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ufam.tcc.security.controller.ui.screen.mainscreen.composable.AddDoorsScreen
import com.ufam.tcc.security.controller.ui.screen.mainscreen.composable.MainScreen
import com.ufam.tcc.security.controller.ui.screen.mainscreen.viewmodel.MainScreenViewModel

@Composable
fun Navigation(
    mainScreenViewModel: MainScreenViewModel,
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {

        composable(
            route = Screen.MainScreen.route,
        ) {
            val state by mainScreenViewModel.mainScreenUiState.collectAsStateWithLifecycle()
            val clearDialog by mainScreenViewModel.clearDialogUiState.collectAsStateWithLifecycle()
            MainScreen(
                state = state,
                dialogState = clearDialog,
                onNavigateToAddScreen = {
                    navController.navigate(Screen.OpenDoorsScreen.route)
                }
            )
        }

        composable(
            route = Screen.OpenDoorsScreen.route,
        ) {
            val state by mainScreenViewModel.addDoorsUiState.collectAsStateWithLifecycle()
            AddDoorsScreen(
                state = state,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
