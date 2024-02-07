package com.danielpasser.categories.repositories

import com.danielpasser.categories.api.ProductsService
import com.danielpasser.categories.models.network.asEntity
import com.danielpasser.categories.models.network.asImageEntityList
import com.danielpasser.categories.room.ProductDao
import com.danielpasser.categories.models.room.ProductWithImageList
import com.danielpasser.categories.models.room.asExternalModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productsService: ProductsService,
    private val productDao: ProductDao
) {
    /**
    app observe changes in db, and automatically update UI
     */
    fun getProductsDao() = productDao.getAllProducts()
        .map {
            it.map(ProductWithImageList::asExternalModel).groupBy { products -> products.category }
        }

    fun getProductsByCategoryDao(category: String) = productDao.getByCategory(category).map {
        it.map(ProductWithImageList::asExternalModel)
    }

    /**
    app doesn't take data directly from server. Instead it saves data in database, and then takes data from DB.
    Single source of truth rule.
     */
    suspend fun getProductsFromServer() {
        val products = productsService.getProducts().products
        productDao.upsertAllProducts(products.map { it.asEntity() })
        productDao.upsertAllImages(products.flatMap { it.asImageEntityList() })


    }
}