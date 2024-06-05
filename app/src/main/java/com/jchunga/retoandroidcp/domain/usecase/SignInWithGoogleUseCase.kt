package com.jchunga.retoandroidcp.domain.usecase;

import com.jchunga.retoandroidcp.domain.model.User
import com.jchunga.retoandroidcp.domain.repository.IAuthRepository;
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(private val repository : IAuthRepository){
    suspend operator fun invoke(idToken: String): User? {
        return repository.signInWithGoogle(idToken)
    }
}
