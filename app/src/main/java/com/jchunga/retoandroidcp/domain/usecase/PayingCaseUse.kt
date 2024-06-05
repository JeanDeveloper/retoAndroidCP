package com.jchunga.retoandroidcp.domain.usecase

import com.jchunga.retoandroidcp.data.repository.ApiRepository
import com.jchunga.retoandroidcp.domain.model.CardInfoRequest
import com.jchunga.retoandroidcp.domain.model.CardInfoResponse
import javax.inject.Inject

class PayingCaseUse @Inject constructor(private val apiRepository: ApiRepository){

    suspend operator fun invoke(cardInfoRequest: CardInfoRequest): CardInfoResponse {
        return apiRepository.pay(cardInfoRequest)
    }

}