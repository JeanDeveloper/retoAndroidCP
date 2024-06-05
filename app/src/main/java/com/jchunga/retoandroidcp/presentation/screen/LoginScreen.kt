package com.jchunga.retoandroidcp.presentation.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.jchunga.retoandroidcp.R
import com.jchunga.retoandroidcp.core.Screen
import com.jchunga.retoandroidcp.presentation.common.LoginTopSection
import com.jchunga.retoandroidcp.presentation.common.SocialMediaButton
import com.jchunga.retoandroidcp.presentation.navigation.localHomeNavController
import com.jchunga.retoandroidcp.presentation.viewmodel.MainViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.installations.Utils
import com.jchunga.retoandroidcp.core.Util
import kotlinx.coroutines.launch
import javax.inject.Inject


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun LoginScreen(
){

    val navController = localHomeNavController.current

    LoginScreenContent(
        navigatorTo = {
            navController.navigate(it.route){
                popUpTo(navController.graph.startDestinationId)
            }
        },
    )

}

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreenContent(
    navigatorTo: (Screen.Home) -> Unit,
    mainViewModel: MainViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        if (task.isSuccessful) {
            val account = task.result
            val idToken = account.idToken
            if (idToken != null) {
                scope.launch {
                    mainViewModel.signInWithGoogle(idToken){
                        navigatorTo(Screen.Home)

                    }
                }
            }
        }
    }


// ESTO ESTA BIEN
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) {
//        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
//        try{
//            val account = task.getResult(ApiException::class.java)
//            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//            mainViewModel.signInWithGoogleCredential(credential){
//                navigatorTo(Screen.Home)
//            }
//        }
//        catch (e: ApiException){
//            Log.d("Paso algo", "LoginScreenContent: ${e.message}")
//        }
//    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {

        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                LoginTopSection()

                SocialMediaButton(
                    icon = R.drawable.google,
                    text = "Google",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    onClick = {

                        val googleSignInClient = GoogleSignIn.getClient(
                            context,
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(Util.Token)
                                .requestEmail()
                                .build()
                        )
                        val signInIntent = googleSignInClient.signInIntent
                        signInLauncher.launch(signInIntent)

//                        val options = GoogleSignInOptions.Builder(
//                            GoogleSignInOptions.DEFAULT_SIGN_IN
//                        )
//                            .requestIdToken(Util.Token)
//                            .requestEmail()
//                            .build()
//                        val googleSignInClient = GoogleSignIn.getClient(context, options)
//                        launcher.launch(googleSignInClient.signInIntent)
                    }
                )

                SocialMediaButton(
                    icon = R.drawable.google,
                    text = "Anonimo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    onClick = {}
                )

            }

        }
    }

}
