package com.danielpasser.categories.models.network

import com.danielpasser.categories.models.room.ImageEntity
import com.danielpasser.categories.models.room.ProductEntity
import com.google.gson.annotations.SerializedName


data class ProductNetwork(

    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("price") val price: Int? = null,
    @SerializedName("discountPercentage") val discountPercentage: Double? = null,
    @SerializedName("rating") val rating: Double? = null,
    @SerializedName("stock") val stock: Int? = null,
    @SerializedName("brand") val brand: String? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null,
    @SerializedName("images") val images: ArrayList<String> = arrayListOf()

)

fun ProductNetwork.asEntity() = ProductEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    discountPercentage = discountPercentage,
    rating = rating,
    stock = stock,
    brand = brand,
    category = category,
    thumbnail = thumbnail,
)

fun ProductNetwork.asImageEntityList(): List<ImageEntity> = images.map { ImageEntity(it, id) }