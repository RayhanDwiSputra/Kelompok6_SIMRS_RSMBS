package com.rsmbs.simrs.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rsmbs.simrs.ui.dokter.AntrianDokterScreen
import com.rsmbs.simrs.ui.dokter.PemeriksaanScreen
import com.rsmbs.simrs.ui.farmasi.FarmasiScreen
import com.rsmbs.simrs.ui.home.HomeScreen
import com.rsmbs.simrs.ui.kasir.KasirDetailScreen
import com.rsmbs.simrs.ui.kasir.KasirScreen
import com.rsmbs.simrs.ui.pendaftaran.AntrianScreen
import com.rsmbs.simrs.ui.pendaftaran.PendaftaranScreen

@Composable
fun SimrsNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigatePendaftaran = { navController.navigate(Screen.Pendaftaran.route) },
                onNavigateDokter = { navController.navigate(Screen.DokterAntrian.route) },
                onNavigateFarmasi = { navController.navigate(Screen.Farmasi.route) },
                onNavigateKasir = { navController.navigate(Screen.Kasir.route) }
            )
        }

        composable(Screen.Pendaftaran.route) {
            PendaftaranScreen(
                onBack = { navController.popBackStack() },
                onLihatAntrian = { navController.navigate(Screen.Antrian.route) }
            )
        }

        composable(Screen.Antrian.route) {
            AntrianScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.DokterAntrian.route) {
            AntrianDokterScreen(
                onBack = { navController.popBackStack() },
                onPilihPasien = { noRegistrasi ->
                    navController.navigate(Screen.Pemeriksaan.createRoute(noRegistrasi))
                }
            )
        }

        composable(
            route = Screen.Pemeriksaan.route,
            arguments = listOf(navArgument("noRegistrasi") { type = NavType.LongType })
        ) { backStackEntry ->
            val noRegistrasi = backStackEntry.arguments?.getLong("noRegistrasi") ?: 0L
            PemeriksaanScreen(
                noRegistrasi = noRegistrasi,
                onBack = { navController.popBackStack() },
                onSelesai = { navController.popBackStack(Screen.DokterAntrian.route, inclusive = false) }
            )
        }

        composable(Screen.Farmasi.route) {
            FarmasiScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Kasir.route) {
            KasirScreen(
                onBack = { navController.popBackStack() },
                onPilihKunjungan = { noRegistrasi ->
                    navController.navigate(Screen.KasirDetail.createRoute(noRegistrasi))
                }
            )
        }

        composable(
            route = Screen.KasirDetail.route,
            arguments = listOf(navArgument("noRegistrasi") { type = NavType.LongType })
        ) { backStackEntry ->
            val noRegistrasi = backStackEntry.arguments?.getLong("noRegistrasi") ?: 0L
            KasirDetailScreen(
                noRegistrasi = noRegistrasi,
                onBack = { navController.popBackStack() },
                onSelesai = { navController.popBackStack(Screen.Kasir.route, inclusive = false) }
            )
        }
    }
}
