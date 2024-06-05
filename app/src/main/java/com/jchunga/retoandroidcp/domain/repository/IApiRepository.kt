package com.jchunga.retoandroidcp.domain.repository

import com.jchunga.retoandroidcp.domain.model.Candies
import com.jchunga.retoandroidcp.domain.model.CardInfoRequest
import com.jchunga.retoandroidcp.domain.model.CardInfoResponse
import com.jchunga.retoandroidcp.domain.model.Premieres

interface IApiRepository  {

    suspend fun getPremierList(): Premieres

    suspend fun getCandyList(): Candies

    suspend fun pay( cardInfoRequest : CardInfoRequest) : CardInfoResponse


}