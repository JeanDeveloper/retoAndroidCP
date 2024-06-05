package com.jchunga.retoandroidcp.data.datasource

import com.jchunga.retoandroidcp.domain.model.Candies
import com.jchunga.retoandroidcp.domain.model.CardInfoRequest
import com.jchunga.retoandroidcp.domain.model.CardInfoResponse
import com.jchunga.retoandroidcp.domain.model.Premieres
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IApiService {

    @GET("premieres")
    suspend fun getPremierList(): Response<Premieres>

    @GET("candystore")
    suspend fun getCandyList(): Response<Candies>

    @POST("complete") // Ruta de la API para iniciar sesión
    suspend fun pay(
        @Body cardInfoRequest: CardInfoRequest
    ): Response<CardInfoResponse> // Cambia "YourResponseModel" por el modelo de respuesta que esperas

}