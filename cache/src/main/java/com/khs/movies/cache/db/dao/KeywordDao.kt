package com.khs.movies.cache.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.khs.movies.cache.db.entity.KeywordEntity

@Dao
interface KeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeyword(vararg keyword: KeywordEntity)
}