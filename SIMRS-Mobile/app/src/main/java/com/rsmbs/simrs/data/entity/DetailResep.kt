package com.rsmbs.simrs.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Baris item obat di dalam satu E-Resep (relasi "Berisi" pada Class Diagram).
 * namaObat & hargaSatuan didenormalisasi agar histori tetap akurat walau
 * harga master obat berubah di kemudian hari.
 */
@Entity(
    tableName = "detail_resep",
    foreignKeys = [
        ForeignKey(entity = EResep::class, parentColumns = ["idResep"], childColumns = ["idResep"]),
        ForeignKey(entity = Obat::class, parentColumns = ["idObat"], childColumns = ["idObat"])
    ],
    indices = [Index("idResep"), Index("idObat")]
)
data class DetailResep(
    @PrimaryKey(autoGenerate = true)
    val idDetail: Long = 0,
    val idResep: Long,
    val idObat: Long,
    val namaObat: String,
    val dosis: String,
    val jumlah: Int,
    val hargaSatuan: Long
)
