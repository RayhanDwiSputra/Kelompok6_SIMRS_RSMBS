package com.rsmbs.simrs.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Rekam Medis Elektronik (RME): hasil diagnosis dan tindakan dokter (UC05/UC06/UC07).
 */
@Entity(
    tableName = "rekam_medis",
    foreignKeys = [
        ForeignKey(
            entity = Pendaftaran::class,
            parentColumns = ["noRegistrasi"],
            childColumns = ["noRegistrasi"]
        )
    ],
    indices = [Index("noRegistrasi")]
)
data class RekamMedis(
    @PrimaryKey(autoGenerate = true)
    val idRME: Long = 0,
    val noRegistrasi: Long,
    val kodeICD10: String,
    val namaDiagnosa: String,
    val anamnesis: String,
    val rencanaTatalaksana: String,
    val tglPeriksa: String
)
