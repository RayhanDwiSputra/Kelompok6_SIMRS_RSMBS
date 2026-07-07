package com.rsmbs.simrs.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Tagihan & pembayaran pasien (UC14 Hitung Tagihan, UC15 Proses Pembayaran).
 * Farmasi hanya boleh menyerahkan obat setelah statusBayar = LUNAS (FR-FM-02).
 */
@Entity(
    tableName = "tagihan",
    foreignKeys = [
        ForeignKey(entity = Pendaftaran::class, parentColumns = ["noRegistrasi"], childColumns = ["noRegistrasi"])
    ],
    indices = [Index("noRegistrasi", unique = true)]
)
data class Tagihan(
    @PrimaryKey(autoGenerate = true)
    val idTagihan: Long = 0,
    val noRegistrasi: Long,
    val biayaJasaDokter: Long,
    val biayaObat: Long,
    val totalBiaya: Long,
    val metodeBayar: String? = null,
    val statusBayar: String = StatusBayar.BELUM_LUNAS,
    val tglBayar: String? = null
)
