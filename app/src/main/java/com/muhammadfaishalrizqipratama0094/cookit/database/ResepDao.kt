package com.muhammadfaishalrizqipratama0094.cookit.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.muhammadfaishalrizqipratama0094.cookit.model.Resep
import kotlinx.coroutines.flow.Flow

@Dao
interface ResepDao {
    @Insert
    suspend fun insert(resep: Resep)

    @Update
    suspend fun update(resep: Resep)

    @Delete
    suspend fun delete(resep: Resep)

    @Query("SELECT * FROM resep ORDER BY tanggalDitambahkan DESC")
    fun getAllResep(): Flow<List<Resep>>

    @Query("SELECT * FROM resep WHERE id = :id")
    suspend fun getResepById(id: Long): Resep?

    @Query("SELECT * FROM resep WHERE nama LIKE '%' || :query || '%'")
    fun searchResep(query: String): Flow<List<Resep>>
}