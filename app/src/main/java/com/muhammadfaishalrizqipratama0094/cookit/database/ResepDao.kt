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

    @Query("SELECT * FROM resep WHERE isRecycled = 0 ORDER BY tanggalDitambahkan DESC")
    fun getAllResep(): Flow<List<Resep>>

    @Query("SELECT * FROM resep WHERE isRecycled = 1 ORDER BY deletedDate DESC")
    fun getRecycledResep(): Flow<List<Resep>>

    @Query("SELECT * FROM resep WHERE id = :id")
    suspend fun getResepById(id: Long): Resep?

    @Query("SELECT * FROM resep WHERE nama LIKE '%' || :query || '%' AND isRecycled = 0")
    fun searchResep(query: String): Flow<List<Resep>>

    @Query("UPDATE resep SET isRecycled = 1, deletedDate = :deletedDate WHERE id = :id")
    suspend fun moveToRecycleBin(id: Long, deletedDate: String)

    @Query("UPDATE resep SET isRecycled = 0, deletedDate = NULL WHERE id = :id")
    suspend fun restoreFromRecycleBin(id: Long)

    @Query("DELETE FROM resep WHERE id = :id")
    suspend fun permanentlyDelete(id: Long)

    @Query("DELETE FROM resep WHERE isRecycled = 1")
    suspend fun emptyRecycleBin()
}