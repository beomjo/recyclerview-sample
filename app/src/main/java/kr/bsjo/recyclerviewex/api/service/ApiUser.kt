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
        @Query("per_page") perPage: Int,
        @Query("sort") param: String = "updated", // created, updated, pushed, full_name. Default: created
        @Query("client_id") cliendId: String = "68dc6a579632099ac35d",
        @Query("client_secret") clientSetret: String = "dccb1170cd682abd6ebf6ec7f96e3c61f40882d4"
    ): Single<List<ModelRepo>>

}