package kr.bsjo.recyclerviewex.api

import kr.bsjo.recyclerviewex.api.gson.GsonHelper
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.URI
import java.util.*
import java.util.concurrent.TimeUnit

object RetrofitAdapter {
    private val TIMEOUT: Long = 20

    private val _instances = Hashtable<String, Retrofit>()

    fun getInstance(baseUri: URI): Retrofit {
        return getInstance(baseUri.toString())
    }

    @Synchronized
    fun getInstance(baseUrl: String): Retrofit {

        if (!_instances.containsKey(baseUrl)) {
            val instance = createInstanceWithUrl(baseUrl)
            _instances[baseUrl] = instance
        }

        return _instances[baseUrl]!!
    }

    private fun createInstanceWithUrl(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonHelper.gson()))
            .build()

    private fun createClient(): OkHttpClient = createHttpClientBuilder().build()

    private fun createHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .cookieJar(JavaNetCookieJar(cookieManager()))
            .addInterceptor(httpLoggingInterceptor())
    }

    private fun cookieManager(): CookieManager {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        return cookieManager
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}
