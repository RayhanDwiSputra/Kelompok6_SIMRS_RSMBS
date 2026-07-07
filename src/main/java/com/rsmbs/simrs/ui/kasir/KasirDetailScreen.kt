package com.rsmbs.simrs.ui.kasir

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsmbs.simrs.SimrsApplication
import com.rsmbs.simrs.ui.SimrsViewModelFactory
import com.rsmbs.simrs.ui.common.SectionCard
import com.rsmbs.simrs.ui.common.SimrsTopBar
import com.rsmbs.simrs.util.CurrencyUtils

private val METODE_BAYAR = listOf("Tunai", "QRIS", "Transfer Bank", "Kartu Debit/Kredit")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KasirDetailScreen(noRegistrasi: Long, onBack: () -> Unit, onSelesai: () -> Unit) {
    val app = LocalContext.current.applicationContext as SimrsApplication
    val viewModel: KasirViewModel = viewModel(factory = SimrsViewModelFactory(app.repository))
    val antrian by viewModel.antrianKasir.collectAsState()
    val detailState by viewModel.detailState.collectAsState()

    val item = antrian.find { it.noRegistrasi == noRegistrasi }
    var metodeDipilih by remember { mutableStateOf(METODE_BAYAR.first()) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(noRegistrasi, item) {
        if (item != null && detailState.tagihan == null) {
            viewModel.hitungTagihan(noRegistrasi, item.jenisPenjamin)
        }
    }

    LaunchedEffect(detailState.pembayaranBerhasil) {
        if (detailState.pembayaranBerhasil) onSelesai()
    }

    Scaffold(topBar = { SimrsTopBar("Detail Tagihan", onBack) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            if (item != null) {
                SectionCard {
                    Text(item.namaPasien, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("${item.poliTujuan} • ${item.jenisPenjamin}", style = MaterialTheme.typography.bodyMedium)
                }
            }

            val tagihan = detailState.tagihan
            if (tagihan != null) {
                SectionCard(modifier = Modifier.padding(top = 16.dp)) {
                    Text("Rincian Tagihan", style = MaterialTheme.typography.titleMedium)
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Jasa Dokter")
                        Text(CurrencyUtils.format(tagihan.biayaJasaDokter))
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Biaya Obat")
                        Text(CurrencyUtils.format(tagihan.biayaObat))
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Text(
                            CurrencyUtils.format(tagihan.totalBiaya),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                if (tagihan.statusBayar == "LUNAS") {
                    SectionCard(modifier = Modifier.padding(top = 16.dp)) {
                        Text(
                            "✔ Pembayaran sudah LUNAS (${tagihan.metodeBayar}).",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    SectionCard(modifier = Modifier.padding(top = 16.dp)) {
                        Text("Metode Pembayaran", style = MaterialTheme.typography.titleMedium)
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            OutlinedTextField(
                                value = metodeDipilih,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth()
                            )
                            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                METODE_BAYAR.forEach {
                                    DropdownMenuItem(text = { Text(it) }, onClick = { metodeDipilih = it; expanded = false })
                                }
                            }
                        }
                        Button(
                            onClick = { viewModel.prosesPembayaran(noRegistrasi, metodeDipilih) },
                            enabled = !detailState.isProcessing,
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                        ) { Text(if (detailState.isProcessing) "Memproses..." else "Proses Pembayaran") }
                    }
                }
            } else {
                Text("Menghitung tagihan...", modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}
