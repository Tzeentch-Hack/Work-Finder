package com.tzeentch.workfinder

sealed class NavigationItem(
    val route: String,
) {
    object Greeting : NavigationItem("greeting")
    object Authorization : NavigationItem("auth")
    object FillQuest : NavigationItem("fil")
    object MainScreen : NavigationItem("main")
    object Settings : NavigationItem("settings")
    object Profile : NavigationItem("profile")

}