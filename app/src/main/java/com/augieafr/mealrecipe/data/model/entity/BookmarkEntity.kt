package com.augieafr.mealrecipe.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_meal")
data class BookmarkEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val thumbUrl: String
)