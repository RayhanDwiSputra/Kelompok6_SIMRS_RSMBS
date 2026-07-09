package com.rsmbs.simrs.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Registrasi kunjungan pasien ke sebuah poliklinik pada tanggal tertentu.
 * Satu Pasien dapat memiliki banyak Pendaftaran (relasi 1..* Melakukan Registrasi).
 */
@Entity(
    tableName = "pendaftaran",
    foreignKeys = [
        ForeignKey(
            entity = Pasien::class,
            parentColumns = ["noRekamMedis"],
            childColumns = ["noRekamMedis"]
        )
    ],
    indices = [Index("noRekamMedis")]
)
data class Pendaftaran(
    @PrimaryKey(autoGenerate = true)
    val noRegistrasi: Long = 0,
    val noRekamMedis: String,
    val tglKunjungan: String, // yyyy-MM-dd
    val poliTujuan: String,
    val noAntrian: Int,
    val noSEP: String? = null,
    val status: String = StatusAntrian.MENUNGGU
)
