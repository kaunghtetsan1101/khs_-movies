package com.khs.movies.cache.db.entity.person

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khs.movies.cache.db.PERSON

@Entity(tableName = PERSON)
data class PersonEntity(
    @PrimaryKey
    @ColumnInfo(name = "ID")
    val id: Int,
    @ColumnInfo(name = "PROFILE_PATH")
    val profilePath: String?,
    @ColumnInfo(name = "ADULT")
    val adult: Boolean,
    @ColumnInfo(name = "NAME")
    val name: String,
    @ColumnInfo(name = "POPULARITY")
    val popularity: Float,
    @ColumnInfo(name = "DETAIL")
    val detailJsonStr: String?
)