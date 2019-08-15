package kr.bsjo.recyclerviewex.api.service

import io.reactivex.Single
import kr.bsjo.recyclerviewex.model.ModelRepo
import kr.bsjo.recyclerviewex.model.ModelUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiUser {

    @GET("users/{user}")
    fun user(@Path("user") user: String): Single<ModelUser>

    @GET("users/{user}/repos")
    fun userRepo(
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<List<ModelRepo>>

}