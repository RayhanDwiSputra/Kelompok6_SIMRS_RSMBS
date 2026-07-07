package com.rsmbs.simrs.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Pendaftaran : Screen("pendaftaran")
    object Antrian : Screen("antrian")
    object DokterAntrian : Screen("dokter_antrian")
    object Pemeriksaan : Screen("pemeriksaan/{noRegistrasi}") {
        fun createRoute(noRegistrasi: Long) = "pemeriksaan/$noRegistrasi"
    }
    object Farmasi : Screen("farmasi")
    object Kasir : Screen("kasir")
    object KasirDetail : Screen("kasir_detail/{noRegistrasi}") {
        fun createRoute(noRegistrasi: Long) = "kasir_detail/$noRegistrasi"
    }
}
