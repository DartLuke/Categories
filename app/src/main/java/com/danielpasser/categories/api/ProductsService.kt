package com.danielpasser.categories.api

import com.danielpasser.categories.models.network.ProductsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {

    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int = 100): ProductsResponse


    companion object {
        private const val BASE_URL = "https://dummyjson.com/"
        //products?limit=100"

        fun create(): ProductsService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductsService::class.java)
        }
    }
}