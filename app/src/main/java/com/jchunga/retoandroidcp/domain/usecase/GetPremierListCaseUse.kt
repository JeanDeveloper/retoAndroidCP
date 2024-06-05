package com.jchunga.retoandroidcp.domain.usecase

import com.jchunga.retoandroidcp.data.repository.ApiRepository
import com.jchunga.retoandroidcp.domain.model.Premieres
import javax.inject.Inject

class GetPremierListCaseUse @Inject constructor(private val apiRepository: ApiRepository ){

    suspend operator fun invoke(): Premieres {
        return apiRepository.getPremierList()
    }

}