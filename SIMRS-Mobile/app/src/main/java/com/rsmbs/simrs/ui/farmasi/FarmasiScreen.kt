package com.rsmbs.simrs.ui.farmasi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsmbs.simrs.SimrsApplication
import com.rsmbs.simrs.data.entity.ResepQueueItem
import com.rsmbs.simrs.data.entity.StatusResep
import com.rsmbs.simrs.ui.SimrsViewModelFactory
import com.rsmbs.simrs.ui.common.EmptyState
import com.rsmbs.simrs.ui.common.FadeSlideIn
import com.rsmbs.simrs.ui.common.IconAvatar
import com.rsmbs.simrs.ui.common.SimrsTopBar
import com.rsmbs.simrs.ui.common.StatusChip
import com.rsmbs.simrs.ui.theme.AmberWarning
import com.rsmbs.simrs.ui.theme.BluePrimary
import com.rsmbs.simrs.ui.theme.GreenSuccess
import com.rsmbs.simrs.ui.theme.LogoGreen
import com.rsmbs.simrs.ui.theme.PastelGreen

@Composable
fun FarmasiScreen(onBack: () -> Unit) {
    val app = LocalContext.current.applicationContext as SimrsApplication
    val viewModel: FarmasiViewModel = viewModel(factory = SimrsViewModelFactory(app.repository))
    val antrian by viewModel.antrianFarmasi.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState) {
        (uiState.pesanSukses ?: uiState.pesanError)?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearPesan()
        }
    }

    Scaffold(
        topBar = { SimrsTopBar("Antrian Farmasi", onBack) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        if (antrian.isEmpty()) {
            Column(modifier = Modifier.padding(padding)) {
                EmptyState("Belum ada resep masuk dari dokter.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(antrian) { index, item ->
                    FadeSlideIn(delayIndex = index) {
                        ResepCard(
                            item = item,
                            onSiapkan = { viewModel.siapkanObat(item.idResep) },
                            onSerahkan = { viewModel.serahkanObat(item.idResep, item.noRegistrasi) }
                        )
                    }
                }
            }
        }
    }
}

private fun LazyListScope.itemsIndexed(
    list: List<ResepQueueItem>,
    content: @Composable (Int, ResepQueueItem) -> Unit
) {
    items(list.size) { index -> content(index, list[index]) }
}

@Composable
private fun ResepCard(item: ResepQueueItem, onSiapkan: () -> Unit, onSerahkan: () -> Unit) {
    val (statusLabel, statusColor) = when (item.statusObat) {
        StatusResep.MENUNGGU -> "Menunggu" to AmberWarning
        StatusResep.DISIAPKAN -> "Disiapkan" to BluePrimary
        else -> "Diserahkan" to GreenSuccess
    }
    val lunas = item.statusBayar == "LUNAS"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                IconAvatar(icon = Icons.Filled.LocalPharmacy, tint = LogoGreen, softBackground = PastelGreen, size = 44.dp)
                Column(modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)) {
                    Text(item.namaPasien, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(item.poliTujuan, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                StatusChip(statusLabel, statusColor)
            }
            Text(
                text = if (lunas) "Status Pembayaran: Lunas" else "Status Pembayaran: Belum Lunas",
                color = if (lunas) GreenSuccess else AmberWarning,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 10.dp)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)) {
                if (item.statusObat == StatusResep.MENUNGGU) {
                    Button(onClick = onSiapkan, shape = RoundedCornerShape(50), modifier = Modifier.weight(1f)) {
                        Text("Verifikasi & Siapkan")
                    }
                }
                if (item.statusObat == StatusResep.DISIAPKAN) {
                    OutlinedButton(
                        onClick = onSerahkan,
                        enabled = lunas,
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (lunas) "Serahkan Obat" else "Menunggu Pembayaran Kasir")
                    }
                }
            }
        }
    }
}
