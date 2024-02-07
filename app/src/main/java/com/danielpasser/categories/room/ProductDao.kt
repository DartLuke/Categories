package com.danielpasser.categories.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.danielpasser.categories.models.room.ImageEntity
import com.danielpasser.categories.models.room.ProductEntity
import com.danielpasser.categories.models.room.ProductWithImageList
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Transaction
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductWithImageList>>

    @Transaction
    @Query("SELECT * FROM products WHERE category=:category")
    fun getByCategory(category: String): Flow<List<ProductWithImageList>>

    @Upsert
    suspend fun upsertAllProducts(products: List<ProductEntity>)

    @Upsert
    suspend fun upsertAllImages(images: List<ImageEntity>)
}