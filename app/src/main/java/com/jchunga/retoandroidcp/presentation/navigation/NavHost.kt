package com.jchunga.retoandroidcp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jchunga.retoandroidcp.core.Screen
import com.jchunga.retoandroidcp.core.Tab
import com.jchunga.retoandroidcp.presentation.common.CandyTab
import com.jchunga.retoandroidcp.presentation.common.PremierTab
import com.jchunga.retoandroidcp.presentation.common.ProfileTab
import com.jchunga.retoandroidcp.presentation.screen.HomeScreen
import com.jchunga.retoandroidcp.presentation.screen.LoginScreen
import com.jchunga.retoandroidcp.presentation.screen.PayScreen
import com.jchunga.retoandroidcp.presentation.screen.SuccessScreen


val localHomeNavController = compositionLocalOf<NavController> { error("HomeNavcontroller") }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(

) {
    val navController = rememberNavController()
    CompositionLocalProvider(value = localHomeNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route
//            startDestination = if (isLoggedIn) {
//                Screen.Home.route
//            } else {
//                Screen.Login.route
//            }
        ) {
            composable(Screen.Login.route){ LoginScreen() }

            composable(Screen.Home.route){ HomeScreen() }

            composable(Screen.Pay.route){ PayScreen() }

            composable(Screen.Success.route){ SuccessScreen() }

            composable(Tab.Premier.route){ PremierTab()}

            composable(Tab.Candy.route){ CandyTab()}

            composable(Tab.Profile.route){ ProfileTab()}

        }
    }
}
