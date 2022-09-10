package com.khs.movies.cache.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khs.movies.cache.db.KEYWORD

@Entity(tableName = KEYWORD)
data class KeywordEntity(
    @PrimaryKey
    @ColumnInfo(name = "ID")
    val id: Int,
    @ColumnInfo(name = "NAME")
    val name: String
)