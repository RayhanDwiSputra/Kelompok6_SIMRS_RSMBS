package com.rsmbs.simrs.data.entity

/** Baris antrian gabungan Pendaftaran + Pasien, dipakai di modul Pendaftaran/Antrian & Dokter. */
data class AntrianItem(
    val noRegistrasi: Long,
    val noRekamMedis: String,
    val namaPasien: String,
    val poliTujuan: String,
    val noAntrian: Int,
    val status: String,
    val jenisPenjamin: String,
    val tglKunjungan: String
)

/** Baris antrian resep di Farmasi, gabungan EResep + Pendaftaran + Pasien. */
data class ResepQueueItem(
    val idResep: Long,
    val noRegistrasi: Long,
    val namaPasien: String,
    val poliTujuan: String,
    val statusObat: String,
    val tglResep: String,
    val statusBayar: String?
)

/** Baris antrian tagihan di Kasir, gabungan Pendaftaran + Pasien (+ status tagihan jika ada). */
data class TagihanQueueItem(
    val noRegistrasi: Long,
    val namaPasien: String,
    val poliTujuan: String,
    val jenisPenjamin: String,
    val status: String,
    val statusBayarTagihan: String?
)
