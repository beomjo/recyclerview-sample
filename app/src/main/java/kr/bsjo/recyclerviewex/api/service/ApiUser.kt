package kr.bsjo.recyclerviewex.api.service

import io.reactivex.Single
import kr.bsjo.recyclerviewex.api.model.ModelRepo
import kr.bsjo.recyclerviewex.api.model.ModelUser
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiUser {

    @GET("users/{user}")
    fun user(@Path("user") user: String): Single<ModelUser>

    @GET("users/{user}/repos")
    fun userRepo(@Path("user") user: String): Single<List<ModelRepo>>

}