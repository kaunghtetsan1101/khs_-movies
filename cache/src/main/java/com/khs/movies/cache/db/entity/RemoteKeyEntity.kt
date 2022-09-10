package com.khs.movies.cache.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khs.movies.cache.db.REMOTE_KEY

@Entity(tableName = REMOTE_KEY)
data class RemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val label: String,
    val nextPageKey: Int?
)