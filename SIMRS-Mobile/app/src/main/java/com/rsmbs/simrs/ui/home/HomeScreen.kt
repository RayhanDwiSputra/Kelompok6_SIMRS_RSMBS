package com.rsmbs.simrs.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsmbs.simrs.R
import com.rsmbs.simrs.SimrsApplication
import com.rsmbs.simrs.ui.SimrsViewModelFactory
import com.rsmbs.simrs.ui.common.FadeSlideIn
import com.rsmbs.simrs.ui.common.ListItemCard
import com.rsmbs.simrs.ui.theme.GradientNavyEnd
import com.rsmbs.simrs.ui.theme.GradientNavyStart
import com.rsmbs.simrs.ui.theme.LogoGold
import com.rsmbs.simrs.ui.theme.LogoGreen
import com.rsmbs.simrs.ui.theme.LogoMaroon
import com.rsmbs.simrs.ui.theme.LogoNavy
import com.rsmbs.simrs.ui.theme.PastelGold
import com.rsmbs.simrs.ui.theme.PastelGreen
import com.rsmbs.simrs.ui.theme.PastelMaroon
import com.rsmbs.simrs.ui.theme.PastelNavy
import com.rsmbs.simrs.util.DateUtils
import java.util.Calendar

private data class ModuleMenu(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val tint: Color,
    val softBackground: Color,
    val onClick: () -> Unit
)

private fun sapaanWaktu(): String {
    val jam = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when {
        jam < 11 -> "Selamat pagi"
        jam < 15 -> "Selamat siang"
        jam < 19 -> "Selamat sore"
        else -> "Selamat malam"
    }
}

@Composable
fun HomeScreen(
    onNavigatePendaftaran: () -> Unit,
    onNavigateDokter: () -> Unit,
    onNavigateFarmasi: () -> Unit,
    onNavigateKasir: () -> Unit
) {
    val app = LocalContext.current.applicationContext as SimrsApplication
    val viewModel: HomeViewModel = viewModel(factory = SimrsViewModelFactory(app.repository))
    val antrianHariIni by viewModel.antrianHariIni.collectAsState()

    val menus = listOf(
        ModuleMenu("Pendaftaran & Antrian", "Registrasi pasien dan kelola antrian poli",
            Icons.Filled.LocalHospital, LogoNavy, PastelNavy, onNavigatePendaftaran),
        ModuleMenu("Rawat Jalan & RME", "Pemeriksaan dokter, diagnosis, dan resep",
            Icons.Filled.MedicalServices, LogoMaroon, PastelMaroon, onNavigateDokter),
        ModuleMenu("Farmasi", "Verifikasi & serahkan obat pasien",
            Icons.Filled.LocalPharmacy, LogoGreen, PastelGreen, onNavigateFarmasi),
        ModuleMenu("Kasir & Billing", "Hitung tagihan dan proses pembayaran",
            Icons.Filled.PointOfSale, LogoGold, PastelGold, onNavigateKasir)
    )

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item { HomeHeader(jumlahAntrian = antrianHariIni.size) }
            item {
                Text(
                    "Modul Layanan",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 12.dp)
                )
            }
            itemsIndexed(this, menus) { index, menu ->
                FadeSlideIn(delayIndex = index) {
                    Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                        ListItemCard(
                            title = menu.title,
                            subtitle = menu.subtitle,
                            icon = menu.icon,
                            iconTint = menu.tint,
                            iconBackground = menu.softBackground,
                            onClick = menu.onClick
                        )
                    }
                }
            }
        }
    }
}

// Helper kecil agar tiap item modul mendapat index untuk animasi berjenjang (staggered).
private fun itemsIndexed(
    scope: LazyListScope,
    list: List<ModuleMenu>,
    content: @Composable (Int, ModuleMenu) -> Unit
) {
    scope.items(list.size) { index -> content(index, list[index]) }
}

@Composable
private fun HomeHeader(jumlahAntrian: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.linearGradient(colors = listOf(GradientNavyStart, GradientNavyEnd)))
            .padding(horizontal = 20.dp, vertical = 28.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_rsmbs),
                        contentDescription = "Logo RS Muhammadiyah Bandung Selatan",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )
                }
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text(
                        "${sapaanWaktu()} \uD83D\uDC4B",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        "SIMRS RS Muhammadiyah\nBandung Selatan",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 22.dp)
                    .background(Color.White.copy(alpha = 0.14f), RoundedCornerShape(18.dp))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        DateUtils.toDisplay(DateUtils.today()),
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "$jumlahAntrian pasien dalam antrian hari ini",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
