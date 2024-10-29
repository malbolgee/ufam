package com.ufam.tcc.security.controller.ui.navigation

sealed class Screen(val route: String) {

    data object MainScreen : Screen("main_screen")
    data object OpenDoorsScreen : Screen("open_doors_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}
