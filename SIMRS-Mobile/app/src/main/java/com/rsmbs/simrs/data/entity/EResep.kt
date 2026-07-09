package com.rsmbs.simrs.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Resep elektronik yang diterbitkan dokter, diteruskan otomatis ke Farmasi (UC08 Buat Resep).
 */
@Entity(
    tableName = "e_resep",
    foreignKeys = [
        ForeignKey(entity = RekamMedis::class, parentColumns = ["idRME"], childColumns = ["idRME"]),
        ForeignKey(entity = Pendaftaran::class, parentColumns = ["noRegistrasi"], childColumns = ["noRegistrasi"])
    ],
    indices = [Index("idRME"), Index("noRegistrasi")]
)
data class EResep(
    @PrimaryKey(autoGenerate = true)
    val idResep: Long = 0,
    val idRME: Long,
    val noRegistrasi: Long,
    val tglResep: String,
    val statusObat: String = StatusResep.MENUNGGU
)
