package com.jchunga.retoandroidcp.data.repository

import com.jchunga.retoandroidcp.data.datasource.IAuthDataSource
import com.jchunga.retoandroidcp.domain.model.User
import com.jchunga.retoandroidcp.domain.repository.IAuthRepository
import javax.inject.Inject

class AuthRepository @Inject constructor(private val remoteDataSource: IAuthDataSource) : IAuthRepository{
    override suspend fun signInWithGoogle(idToken: String): User? {
        return remoteDataSource.signInWithGoogle(idToken)
    }


}