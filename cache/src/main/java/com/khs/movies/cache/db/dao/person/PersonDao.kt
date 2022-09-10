package com.khs.movies.cache.db.dao.person

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khs.movies.cache.db.entity.person.PersonEntity

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(vararg person: PersonEntity)

    @Query("SELECT * FROM PERSON ORDER BY POPULARITY DESC")
    fun getPersons(): PagingSource<Int, PersonEntity>

    @Query("SELECT * FROM PERSON WHERE ID = :id")
    suspend fun getPersonById(id: Int): PersonEntity

    @Query("DELETE FROM PERSON")
    suspend fun deleteAll()
}