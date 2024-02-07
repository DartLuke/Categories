package com.danielpasser.categories.models.network

import com.google.gson.annotations.SerializedName


data class ProductsResponse (

  @SerializedName("products" ) val products : ArrayList<ProductNetwork> = arrayListOf(),
  @SerializedName("total"    ) val total    : Int?                = null,
  @SerializedName("skip"     ) val skip     : Int?                = null,
  @SerializedName("limit"    ) val limit    : Int?                = null

)