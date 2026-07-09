package com.rsmbs.simrs.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Hasil pemeriksaan awal (vital sign) sebelum pasien diperiksa dokter (UC10 Input Asesmen).
 */
@Entity(
    tableName = "asesmen_medis",
    foreignKeys = [
        ForeignKey(
            entity = Pendaftaran::class,
            parentColumns = ["noRegistrasi"],
            childColumns = ["noRegistrasi"]
        )
    ],
    indices = [Index("noRegistrasi")]
)
data class AsesmenMedis(
    @PrimaryKey(autoGenerate = true)
    val idAsesmen: Long = 0,
    val noRegistrasi: Long,
    val tekananDarah: String,
    val nadi: Int,
    val suhuTubuh: Double,
    val beratBadan: Double,
    val keluhanUtama: String
)
