package com.jchunga.retoandroidcp.data.datasource

import com.jchunga.retoandroidcp.domain.model.Candies
import com.jchunga.retoandroidcp.domain.model.CardInfoRequest
import com.jchunga.retoandroidcp.domain.model.CardInfoResponse
import com.jchunga.retoandroidcp.domain.model.Premieres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import javax.inject.Inject

class ApiService @Inject constructor( private val iApiService: IApiService ) {

    suspend fun getPremierList(): Premieres {
        return withContext(Dispatchers.IO){
            try {
                val response = iApiService.getPremierList()
                response.body()!!

            }catch (e:SocketTimeoutException){
                Premieres()
            }
        }
    }

    suspend fun getCandyList(): Candies {
        return withContext(Dispatchers.IO){
            try {
                val response = iApiService.getCandyList()
                response.body()!!

            }catch (e:SocketTimeoutException){
                Candies()
            }
        }
    }

    suspend fun pay(cardInfoRequest: CardInfoRequest): CardInfoResponse {
        return withContext(Dispatchers.IO){
            try {
                val response = iApiService.pay(cardInfoRequest)
                response.body()!!

            }catch (e:SocketTimeoutException){
                CardInfoResponse()
            }
        }
    }

}