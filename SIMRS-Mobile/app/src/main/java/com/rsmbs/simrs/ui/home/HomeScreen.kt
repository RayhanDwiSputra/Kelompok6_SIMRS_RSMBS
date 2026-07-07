package com.rsmbs.simrs.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rsmbs.simrs.R
import com.rsmbs.simrs.ui.theme.LogoGold
import com.rsmbs.simrs.ui.theme.LogoGreen
import com.rsmbs.simrs.ui.theme.LogoMaroon
import com.rsmbs.simrs.ui.theme.LogoNavy

private data class ModuleMenu(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

@Composable
fun HomeScreen(
    onNavigatePendaftaran: () -> Unit,
    onNavigateDokter: () -> Unit,
    onNavigateFarmasi: () -> Unit,
    onNavigateKasir: () -> Unit
) {
    val menus = listOf(
        ModuleMenu(
            "Pendaftaran & Antrian",
            "Registrasi pasien dan kelola antrian poli",
            Icons.Filled.LocalHospital,
            LogoNavy,
            onNavigatePendaftaran
        ),
        ModuleMenu(
            "Rawat Jalan & RME",
            "Pemeriksaan dokter, diagnosis, dan resep",
            Icons.Filled.MedicalServices,
            LogoMaroon,
            onNavigateDokter
        ),
        ModuleMenu(
            "Farmasi",
            "Verifikasi & serahkan obat pasien",
            Icons.Filled.LocalPharmacy,
            LogoGreen,
            onNavigateFarmasi
        ),
        ModuleMenu(
            "Kasir & Billing",
            "Hitung tagihan dan proses pembayaran",
            Icons.Filled.PointOfSale,
            LogoGold.copy(alpha = 1f),
            onNavigateKasir
        )
    )

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HomeHeader()
            Text(
                text = "Pilih modul layanan",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(menus) { menu -> ModuleCard(menu) }
            }
        }
    }
}

@Composable
private fun HomeHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(LogoNavy, LogoNavy.copy(alpha = 0.85f))
                )
            )
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_rsmbs),
                    contentDescription = "Logo RS Muhammadiyah Bandung Selatan",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )
            }
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    "SIMRS",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "RS Muhammadiyah Bandung Selatan",
                    color = Color.White.copy(alpha = 0.85f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun ModuleCard(menu: ModuleMenu) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = menu.onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(menu.color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(menu.icon, contentDescription = menu.title, tint = Color.White)
            }
            Text(
                text = menu.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                text = menu.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
