package com.rsmbs.simrs.ui.farmasi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.rsmbs.simrs.ui.common.SimrsTopBar
import com.rsmbs.simrs.ui.common.StatusChip
import com.rsmbs.simrs.ui.theme.AmberWarning
import com.rsmbs.simrs.ui.theme.BluePrimary
import com.rsmbs.simrs.ui.theme.GreenSuccess
import com.rsmbs.simrs.util.CurrencyUtils

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
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(antrian) { item ->
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(item.namaPasien, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                StatusChip(statusLabel, statusColor)
            }
            Text(item.poliTujuan, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = if (lunas) "Status Pembayaran: Lunas" else "Status Pembayaran: Belum Lunas",
                color = if (lunas) GreenSuccess else AmberWarning,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
                if (item.statusObat == StatusResep.MENUNGGU) {
                    Button(onClick = onSiapkan, modifier = Modifier.weight(1f)) {
                        Text("Verifikasi & Siapkan")
                    }
                }
                if (item.statusObat == StatusResep.DISIAPKAN) {
                    OutlinedButton(
                        onClick = onSerahkan,
                        enabled = lunas,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (lunas) "Serahkan Obat" else "Menunggu Pembayaran Kasir")
                    }
                }
            }
        }
    }
}
