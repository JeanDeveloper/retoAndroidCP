package com.jchunga.retoandroidcp.domain.repository

import com.jchunga.retoandroidcp.domain.model.User

interface IAuthRepository {

    suspend fun signInWithGoogle(idToken: String): User?

}