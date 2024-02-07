package com.danielpasser.categories.di

import com.danielpasser.categories.api.ProductsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun provideProductsService(): ProductsService = ProductsService.create()
}