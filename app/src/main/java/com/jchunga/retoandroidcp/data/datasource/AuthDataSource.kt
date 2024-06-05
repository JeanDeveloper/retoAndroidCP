package com.jchunga.retoandroidcp.data.datasource

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jchunga.retoandroidcp.core.AuthRes
import com.jchunga.retoandroidcp.domain.model.User
import kotlinx.coroutines.tasks.await

interface IAuthDataSource {

    suspend fun signInWithGoogle(idToken: String): User?

    suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthRes<FirebaseUser>?

}

class FirebaseDataSource @Inject constructor( private val auth: FirebaseAuth ) : IAuthDataSource {

//    private val auth = FirebaseAuth.getInstance()

    override suspend fun signInWithGoogle(idToken: String): User? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = auth.signInWithCredential(credential).await()
        return authResult.user?.let {
            User(
                uid = it.uid,
                displayName = it.displayName ?: "",
                email = it.email ?: "",
                photoUrl = it.photoUrl.toString()
            )
        }
    }

    override suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthRes<FirebaseUser>? {
        return try {
            val firebaseUser = auth.signInWithCredential(credential).await()
            firebaseUser.user?.let{
                AuthRes.Success(it)
            } ?: throw Exception("Sign In con Google Falló.")

        } catch (e:Exception){
            AuthRes.Error(e.message ?: "Google Sign con Google Falló,")
        }
    }

    fun handleSignInResult(task: Task<GoogleSignInAccount>): AuthRes<GoogleSignInAccount>? {
        return try{
            val account = task.getResult(ApiException::class.java)
            AuthRes.Success(account)
        }catch (e: ApiException){
            AuthRes.Error(e.message ?: "Google Sign In Fallido" )
        }
    }


}