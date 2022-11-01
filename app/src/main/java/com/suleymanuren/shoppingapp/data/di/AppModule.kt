package com.suleymanuren.shoppingapp.data.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.suleymanuren.shoppingapp.data.remote.api.ProductService
import com.suleymanuren.shoppingapp.data.remote.source.ProductRemoteDataSource
import com.suleymanuren.shoppingapp.data.remote.source.impl.ProductRemoteDataSourceImpl
import com.suleymanuren.shoppingapp.domain.repository.ProductsRepository
import com.suleymanuren.shoppingapp.domain.repository.impl.ProductsRepositoryImpl
import com.suleymanuren.shoppingapp.util.SharedPrefConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SharedPrefConstants.LOCAL_SHARED_PREF, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService = retrofit.create(ProductService::class.java)

    @Singleton
    @Provides
    fun provideProductRemoteDataSource(productService: ProductService): ProductRemoteDataSource =
        ProductRemoteDataSourceImpl(productService)

    @Singleton
    @Provides
    fun provideProductRepository(productRemoteDataSource: ProductRemoteDataSource): ProductsRepository =
        ProductsRepositoryImpl(productRemoteDataSource)

}