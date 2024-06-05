package com.jchunga.retoandroidcp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import com.jchunga.retoandroidcp.presentation.navigation.Navigation
import com.jchunga.retoandroidcp.ui.theme.RetoAndroidCPTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val firebaseAuth = FirebaseAuth.getInstance()
            RetoAndroidCPTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation(firebaseAuth  = firebaseAuth)
                }
            }
        }
    }
}
