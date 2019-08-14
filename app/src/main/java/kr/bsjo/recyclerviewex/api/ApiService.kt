package kr.bsjo.recyclerviewex.api

import kr.bsjo.recyclerviewex.api.service.ApiUser

object ApiService {
    private const val baseUrl = "https://api.github.com/"

    fun github() = RetrofitAdapter.getInstance(baseUrl)
        .create(ApiUser::class.java)
}