package com.jchunga.retoandroidcp.data.repository

import com.jchunga.retoandroidcp.data.datasource.ApiService
import com.jchunga.retoandroidcp.domain.model.Candies
import com.jchunga.retoandroidcp.domain.model.CardInfoRequest
import com.jchunga.retoandroidcp.domain.model.CardInfoResponse
import com.jchunga.retoandroidcp.domain.model.Premieres
import com.jchunga.retoandroidcp.domain.repository.IApiRepository
import javax.inject.Inject

class ApiRepository @Inject constructor(private val authService: ApiService) : IApiRepository {
    override suspend fun getPremierList(): Premieres {
        return authService.getPremierList()
    }

    override suspend fun getCandyList(): Candies {
        return authService.getCandyList()
    }

    override suspend fun pay(cardInfoRequest: CardInfoRequest): CardInfoResponse {
        return authService.pay(cardInfoRequest)
    }
}