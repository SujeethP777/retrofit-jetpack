package com.dqitech.productretrofit3

import com.google.gson.annotations.SerializedName

class Product {

    @SerializedName("id")
     var id: Int = 0

    @SerializedName("title")
    var title : String = ""

    @SerializedName("description")
    var description : String = ""

    @SerializedName("price")
    var price : Int = 0

    @SerializedName("brand")
    var brand : String = ""

    @SerializedName("images")
    lateinit var images : Array<String>

}