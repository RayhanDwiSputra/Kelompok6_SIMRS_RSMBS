package com.rsmbs.simrs.ui.pendaftaran

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.rsmbs.simrs.ui.common.FadeSlideIn
import com.rsmbs.simrs.ui.common.SimrsTopBar
import com.rsmbs.simrs.ui.common.StatusChip
import com.rsmbs.simrs.ui.theme.AmberWarning
import com.rsmbs.simrs.ui.theme.BluePrimary
import com.rsmbs.simrs.ui.theme.GreenSuccess
import com.rsmbs.simrs.ui.theme.NavyPrimary
import com.rsmbs.simrs.ui.theme.PastelNavy

@Composable
fun AntrianScreen(onBack: () -> Unit) {
    val app = LocalContext.current.applicationContext as SimrsApplication
    val viewModel: PendaftaranViewModel = viewModel(factory = SimrsViewModelFactory(app.repository))
    val antrian by viewModel.antrianHariIni.collectAsState()

    Scaffold(topBar = { SimrsTopBar("Antrian Hari Ini", onBack) }) { padding ->
        if (antrian.isEmpty()) {
            Column(modifier = Modifier.padding(padding)) {
                EmptyState("Belum ada pendaftaran hari ini. Antrian akan muncul di sini.")
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
                        AntrianCard(item) { viewModel.panggilAntrian(item.noRegistrasi) }
                    }
                }
            }
        }
    }
}

private fun androidx.compose.foundation.lazy.LazyListScope.itemsIndexed(
    list: List<AntrianItem>,
    content: @Composable (Int, AntrianItem) -> Unit
) {
    items(list.size) { index -> content(index, list[index]) }
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
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(PastelNavy, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("${item.noAntrian}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = NavyPrimary)
            }
            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.namaPasien, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    StatusChip(statusLabel, statusColor)
                }
                Text("${item.poliTujuan} • ${item.jenisPenjamin}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("NRM: ${item.noRekamMedis}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

                if (item.status == StatusAntrian.MENUNGGU) {
                    Button(
                        onClick = onPanggil,
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) { Text("Panggil Pasien") }
                }
            }
        }
    }
}
