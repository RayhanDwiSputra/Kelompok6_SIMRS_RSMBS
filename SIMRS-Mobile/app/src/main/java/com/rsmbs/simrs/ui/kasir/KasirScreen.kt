package com.rsmbs.simrs.ui.kasir

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsmbs.simrs.SimrsApplication
import com.rsmbs.simrs.data.entity.TagihanQueueItem
import com.rsmbs.simrs.ui.SimrsViewModelFactory
import com.rsmbs.simrs.ui.common.EmptyState
import com.rsmbs.simrs.ui.common.SimrsTopBar
import com.rsmbs.simrs.ui.common.StatusChip
import com.rsmbs.simrs.ui.theme.AmberWarning

@Composable
fun KasirScreen(onBack: () -> Unit, onPilihKunjungan: (Long) -> Unit) {
    val app = LocalContext.current.applicationContext as SimrsApplication
    val viewModel: KasirViewModel = viewModel(factory = SimrsViewModelFactory(app.repository))
    val antrian by viewModel.antrianKasir.collectAsState()

    Scaffold(topBar = { SimrsTopBar("Antrian Kasir & Billing", onBack) }) { padding ->
        if (antrian.isEmpty()) {
            Column(modifier = Modifier.padding(padding)) {
                EmptyState("Belum ada kunjungan yang siap ditagih.")
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
                    KunjunganCard(item) { onPilihKunjungan(item.noRegistrasi) }
                }
            }
        }
    }
}

@Composable
private fun KunjunganCard(item: TagihanQueueItem, onClick: () -> Unit) {
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
                Text("${item.poliTujuan} • ${item.jenisPenjamin}", style = MaterialTheme.typography.bodyMedium)
            }
            StatusChip(
                if (item.statusBayarTagihan == "LUNAS") "Lunas" else "Belum Ditagih",
                AmberWarning
            )
        }
    }
}
