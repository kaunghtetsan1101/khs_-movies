package com.khs.movies.cache.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khs.movies.cache.db.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: RemoteKeyEntity)

    @Query("SELECT * FROM REMOTE_KEY WHERE label = :label")
    suspend fun getRemoteKeyByLabel(label: String): RemoteKeyEntity

    @Query("DELETE FROM REMOTE_KEY WHERE label = :label")
    suspend fun deleteByLabel(label: String)
}