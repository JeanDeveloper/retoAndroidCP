package com.jchunga.retoandroidcp.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jchunga.retoandroidcp.core.Tab
import com.jchunga.retoandroidcp.presentation.common.CandyTab
import com.jchunga.retoandroidcp.presentation.common.PremierTab
import com.jchunga.retoandroidcp.presentation.common.ProfileTab
import com.jchunga.retoandroidcp.presentation.navigation.localHomeNavController
import com.jchunga.retoandroidcp.presentation.viewmodel.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
){
    val selectedTab by mainViewModel.selectedTab.observeAsState(Tab.Premier)

    Scaffold(
        bottomBar = { MenuBottomNavigation() }
    ){
        val selectedTab by mainViewModel.selectedTab.observeAsState(Tab.Premier)
        when (selectedTab) {
            Tab.Premier -> PremierTab()
            Tab.Candy -> CandyTab()
            Tab.Profile -> ProfileTab()
        }
    }
}


@Composable
fun MenuBottomNavigation(
    mainViewModel: MainViewModel = hiltViewModel(),
){
    val tabs = listOf(
        Tab.Premier,
        Tab.Candy,
        Tab.Profile
    )

    val navController = localHomeNavController.current
    val selectedTab by mainViewModel.selectedTab.observeAsState(Tab.Premier)
    NavigationBar {
        tabs.forEach {
            tab ->
            NavigationBarItem(
                selected = selectedTab == tab,
                onClick = { mainViewModel.selectTab(tab) },
                icon = { Icon(painterResource(id = tab.icon),contentDescription = tab.title) },
                label = { Text(tab.title) }
            )
        }

    }

}