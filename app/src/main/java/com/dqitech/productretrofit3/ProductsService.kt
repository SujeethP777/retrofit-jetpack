package com.dqitech.productretrofit3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {

    @GET("products")
    fun getProducts() : Call<ProductsResponse>

    @GET("products/{product_id}")
    fun getProduct(@Path("product_id") productId : Int) : Call<Product>
}