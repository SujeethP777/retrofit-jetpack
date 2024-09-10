package com.dqitech.productretrofit3

import com.google.gson.annotations.SerializedName

class ProductsResponse {

    @SerializedName("products")
    lateinit var products : ArrayList<Product>
}