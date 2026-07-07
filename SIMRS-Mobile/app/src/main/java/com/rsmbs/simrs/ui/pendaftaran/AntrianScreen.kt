package com.rsmbs.simrs.ui.pendaftaran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsmbs.simrs.SimrsApplication
import com.rsmbs.simrs.data.entity.AntrianItem
import com.rsmbs.simrs.data.entity.StatusAntrian
import com.rsmbs.simrs.ui.SimrsViewModelFactory
import com.rsmbs.simrs.ui.common.EmptyState
import com.rsmbs.simrs.ui.common.SimrsTopBar
import com.rsmbs.simrs.ui.common.StatusChip
import com.rsmbs.simrs.ui.theme.AmberWarning
import com.rsmbs.simrs.ui.theme.BluePrimary
import com.rsmbs.simrs.ui.theme.GreenSuccess
import com.rsmbs.simrs.ui.theme.NavyPrimary

@Composable
fun AntrianScreen(onBack: () -> Unit) {
    val app = LocalContext.current.applicationContext as SimrsApplication
    val viewModel: PendaftaranViewModel = viewModel(factory = SimrsViewModelFactory(app.repository))
    val antrian by viewModel.antrianHariIni.collectAsState()

    Scaffold(topBar = { SimrsTopBar("Antrian Hari Ini", onBack) }) { padding ->
        if (antrian.isEmpty()) {
            Column(modifier = Modifier.padding(padding)) {
                EmptyState("Belum ada pendaftaran hari ini.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(antrian) { item -> AntrianCard(item) { viewModel.panggilAntrian(item.noRegistrasi) } }
            }
        }
    }
}

@Composable
private fun AntrianCard(item: AntrianItem, onPanggil: () -> Unit) {
    val (statusLabel, statusColor) = when (item.status) {
        StatusAntrian.MENUNGGU -> "Menunggu" to AmberWarning
        StatusAntrian.DIPANGGIL -> "Dipanggil" to BluePrimary
        StatusAntrian.DIPERIKSA -> "Diperiksa" to NavyPrimary
        StatusAntrian.SELESAI_PERIKSA -> "Selesai Periksa" to GreenSuccess
        else -> item.status to GreenSuccess
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "No. ${item.noAntrian}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                StatusChip(statusLabel, statusColor)
            }
            Text(item.namaPasien, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 4.dp))
            Text("${item.poliTujuan} • ${item.jenisPenjamin}", style = MaterialTheme.typography.bodyMedium)
            Text("NRM: ${item.noRekamMedis}", style = MaterialTheme.typography.bodyMedium)

            if (item.status == StatusAntrian.MENUNGGU) {
                Button(
                    onClick = onPanggil,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) { Text("Panggil Pasien") }
            }
        }
    }
}
