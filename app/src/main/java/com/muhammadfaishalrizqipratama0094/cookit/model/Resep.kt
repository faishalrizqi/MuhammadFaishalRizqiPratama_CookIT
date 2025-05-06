package com.muhammadfaishalrizqipratama0094.cookit.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resep")
data class Resep(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val bahan: String,
    val langkah: String,
    val kategori: String,
    val waktuMasak: Int,
    val tanggalDitambahkan: String,
    val imageUrl: String
)