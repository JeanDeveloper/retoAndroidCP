package com.jchunga.retoandroidcp.domain.usecase

import com.jchunga.retoandroidcp.data.repository.ApiRepository
import com.jchunga.retoandroidcp.domain.model.Candies
import javax.inject.Inject

class GetCandyListCaseUse @Inject constructor(private val apiRepository: ApiRepository){

    suspend operator fun invoke() : Candies = apiRepository.getCandyList()

}