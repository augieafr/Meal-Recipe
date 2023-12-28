package com.augieafr.mealrecipe.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.augieafr.mealrecipe.data.model.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Query("SELECT * FROM bookmarked_meal")
    fun getAllBookmarkedMeal(): Flow<List<BookmarkEntity>>

    @Query("SELECT COUNT() FROM bookmarked_meal WHERE id = :id")
    suspend fun isBookmarked(id: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(meal: BookmarkEntity)

    @Delete
    suspend fun removeBookmark(meal: BookmarkEntity)
}