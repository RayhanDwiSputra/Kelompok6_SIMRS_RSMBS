package com.rsmbs.simrs.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Master data pasien. noRekamMedis (NRM) adalah kunci unik yang dipakai
 * lintas modul (Pendaftaran, RME, Billing) sesuai Class Diagram SIMRS.
 */
@Entity(tableName = "pasien")
data class Pasien(
    @PrimaryKey
    val noRekamMedis: String,
    val nik: String,
    val namaPasien: String,
    val tanggalLahir: String, // format: yyyy-MM-dd
    val jenisKelamin: String, // "L" atau "P"
    val jenisPenjamin: String // "UMUM", "BPJS", "ASURANSI"
)
