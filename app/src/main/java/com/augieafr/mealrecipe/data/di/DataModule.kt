package com.augieafr.mealrecipe.data.di

import android.content.Context
import androidx.room.Room
import com.augieafr.mealrecipe.BuildConfig
import com.augieafr.mealrecipe.data.local.MealDao
import com.augieafr.mealrecipe.data.local.MealDatabase
import com.augieafr.mealrecipe.data.network.ApiService
import com.augieafr.mealrecipe.data.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideMealDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(appContext, MealDatabase::class.java, "meal_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideMealDao(mealDatabase: MealDatabase): MealDao {
        return mealDatabase.todoDao()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideMealRepository(apiService: ApiService, mealDao: MealDao): MealRepository {
        return MealRepository(apiService, mealDao)
    }
}