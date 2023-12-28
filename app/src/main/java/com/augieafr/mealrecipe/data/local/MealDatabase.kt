package com.augieafr.mealrecipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.augieafr.mealrecipe.data.model.entity.BookmarkEntity

@Database(version = 1, entities = [BookmarkEntity::class])
abstract class MealDatabase : RoomDatabase() {
    abstract fun todoDao(): MealDao
}