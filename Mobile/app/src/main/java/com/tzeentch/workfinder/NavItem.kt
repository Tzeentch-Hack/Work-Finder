package com.tzeentch.workfinder

sealed class NavigationItem(
    val route: String,
) {
    object Greeting : NavigationItem("greeting")
    object Authorization : NavigationItem("auth")
    object Registration : NavigationItem("reg")
    object Questionary:NavigationItem("ques")
    object Settings : NavigationItem("settings")
    object Air : NavigationItem("air")

}