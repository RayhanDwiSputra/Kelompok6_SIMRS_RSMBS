package com.rsmbs.simrs.ui.dokter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsmbs.simrs.SimrsApplication
import com.rsmbs.simrs.data.entity.AntrianItem
import com.rsmbs.simrs.ui.SimrsViewModelFactory
import com.rsmbs.simrs.ui.common.EmptyState
import com.rsmbs.simrs.ui.common.SimrsTopBar
import com.rsmbs.simrs.ui.common.StatusChip
import com.rsmbs.simrs.ui.theme.BluePrimary

@Composable
fun AntrianDokterScreen(onBack: () -> Unit, onPilihPasien: (Long) -> Unit) {
    val app = LocalContext.current.applicationContext as SimrsApplication
    val viewModel: DokterViewModel = viewModel(factory = SimrsViewModelFactory(app.repository))
    val antrian by viewModel.antrianDokter.collectAsState()

    Scaffold(topBar = { SimrsTopBar("Antrian Pemeriksaan Dokter", onBack) }) { padding ->
        if (antrian.isEmpty()) {
            Column(modifier = Modifier.padding(padding)) {
                EmptyState("Belum ada pasien yang dipanggil untuk pemeriksaan.")
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
                    PasienDokterCard(item) { onPilihPasien(item.noRegistrasi) }
                }
            }
        }
    }
}

@Composable
private fun PasienDokterCard(item: AntrianItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(item.namaPasien, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("${item.poliTujuan} • NRM: ${item.noRekamMedis}", style = MaterialTheme.typography.bodyMedium)
            }
            StatusChip("Antrian ${item.noAntrian}", BluePrimary)
        }
    }
}
