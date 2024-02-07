package com.danielpasser.categories.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey val image: String,
    val productId: Int
)

