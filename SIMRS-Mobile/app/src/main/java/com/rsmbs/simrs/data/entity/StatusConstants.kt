package com.rsmbs.simrs.data.entity

/** Status alur kunjungan pasien (UC01 Kelola Antrian & UC04 Register Pasien). */
object StatusAntrian {
    const val MENUNGGU = "MENUNGGU"
    const val DIPANGGIL = "DIPANGGIL"
    const val DIPERIKSA = "DIPERIKSA"
    const val SELESAI_PERIKSA = "SELESAI_PERIKSA"
    const val SELESAI = "SELESAI"
}

/** Status resep di modul Farmasi (UC13 Serahkan Obat). */
object StatusResep {
    const val MENUNGGU = "MENUNGGU"
    const val DISIAPKAN = "DISIAPKAN"
    const val DISERAHKAN = "DISERAHKAN"
}

/** Status pembayaran di modul Kasir (UC14/UC15). */
object StatusBayar {
    const val BELUM_LUNAS = "BELUM_LUNAS"
    const val LUNAS = "LUNAS"
}
