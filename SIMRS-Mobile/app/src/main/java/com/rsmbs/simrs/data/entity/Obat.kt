package com.rsmbs.simrs.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Master data obat & stok gudang farmasi (Class DetailObat pada Class Diagram).
 */
@Entity(tableName = "obat")
data class Obat(
    @PrimaryKey(autoGenerate = true)
    val idObat: Long = 0,
    val namaObat: String,
    val satuan: String,
    val stok: Int,
    val hargaSatuan: Long,
    val stokMinimum: Int = 10
)
