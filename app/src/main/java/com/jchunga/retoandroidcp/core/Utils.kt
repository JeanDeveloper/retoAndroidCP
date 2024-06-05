package com.jchunga.retoandroidcp.core

import com.jchunga.retoandroidcp.R

object Util {
    const val Base ="https://cp-staging.onrender.com/v1/"
    const val Token ="457597510178-865c3iv4v2ls8r0ltq7bn8iclbgdo45f.apps.googleusercontent.com"
}

sealed class Screen(val route: String) {
    object Login : Screen("Login Screen")
    object Home  : Screen("Home Screen")
    object Pay   : Screen("Pay Screen")
    object Success : Screen("Success Screen")
}

sealed class Tab(val title: String, val route: String, val icon: Int) {
    object Premier : Tab("Peliculas", "Premier Tab", R.drawable.movie)
    object Candy  : Tab("Comidas", "Candy Tab", R.drawable.candy)
    object Profile   : Tab("Perfil", "Profile Tab", R.drawable.profile)
}

sealed class AuthRes<out T> {
    data class Success<T>(val data: T) : AuthRes<T>()
    data class Error(val message: String) : AuthRes<Nothing>()
}