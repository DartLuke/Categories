package com.danielpasser.categories.models.room

import androidx.room.Embedded
import androidx.room.Relation
import com.danielpasser.categories.models.Product

data class ProductWithImageList(
    @Embedded val product: ProductEntity,
    @Relation(parentColumn = "id", entityColumn = "productId")
    val images: List<ImageEntity> = arrayListOf()
)

fun ProductWithImageList.asExternalModel() = Product(
    id = product.id,
    title = product.title,
    description = product.description,
    price = product.price,
    discountPercentage = product.discountPercentage,
    rating = product.rating,
    stock = product.stock,
    brand = product.brand,
    category = product.category,
    thumbnail = product.thumbnail,
    images = images.map { it.image }
)
