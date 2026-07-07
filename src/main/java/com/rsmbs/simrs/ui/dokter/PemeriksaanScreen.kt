package com.rsmbs.simrs.ui.dokter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.rsmbs.simrs.data.entity.Obat
import com.rsmbs.simrs.ui.SimrsViewModelFactory
import com.rsmbs.simrs.ui.common.SectionCard
import com.rsmbs.simrs.ui.common.SimrsTopBar
import com.rsmbs.simrs.util.CurrencyUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PemeriksaanScreen(noRegistrasi: Long, onBack: () -> Unit, onSelesai: () -> Unit) {
    val app = LocalContext.current.applicationContext as SimrsApplication
    val viewModel: DokterViewModel = viewModel(factory = SimrsViewModelFactory(app.repository))
    val uiState by viewModel.uiState.collectAsState()
    val daftarObat by viewModel.daftarObat.collectAsState()

    var tekananDarah by remember { mutableStateOf("") }
    var nadi by remember { mutableStateOf("") }
    var suhuTubuh by remember { mutableStateOf("") }
    var beratBadan by remember { mutableStateOf("") }
    var keluhanUtama by remember { mutableStateOf("") }
    var kodeICD10 by remember { mutableStateOf("") }
    var namaDiagnosa by remember { mutableStateOf("") }
    var anamnesis by remember { mutableStateOf("") }
    var rencanaTatalaksana by remember { mutableStateOf("") }

    var showResepDialog by remember { mutableStateOf(false) }

    if (uiState.berhasilSimpan) {
        androidx.compose.runtime.LaunchedEffect(Unit) { onSelesai() }
    }

    Scaffold(topBar = { SimrsTopBar("Pemeriksaan Pasien", onBack) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionCard {
                Text("Asesmen / Vital Sign", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = tekananDarah, onValueChange = { tekananDarah = it },
                    label = { Text("Tekanan Darah (mis. 120/80)") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    OutlinedTextField(
                        value = nadi, onValueChange = { nadi = it.filter(Char::isDigit) },
                        label = { Text("Nadi (/menit)") }, singleLine = true,
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )
                    OutlinedTextField(
                        value = suhuTubuh, onValueChange = { suhuTubuh = it },
                        label = { Text("Suhu (°C)") }, singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }
                OutlinedTextField(
                    value = beratBadan, onValueChange = { beratBadan = it },
                    label = { Text("Berat Badan (kg)") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = keluhanUtama, onValueChange = { keluhanUtama = it },
                    label = { Text("Keluhan Utama") },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
            }

            SectionCard {
                Text("Diagnosis & Tindakan (UC07)", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = kodeICD10, onValueChange = { kodeICD10 = it },
                    label = { Text("Kode ICD-10") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = namaDiagnosa, onValueChange = { namaDiagnosa = it },
                    label = { Text("Nama Diagnosa") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = anamnesis, onValueChange = { anamnesis = it },
                    label = { Text("Anamnesis") },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = rencanaTatalaksana, onValueChange = { rencanaTatalaksana = it },
                    label = { Text("Rencana Tatalaksana") },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
            }

            SectionCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("E-Resep (UC08)", style = MaterialTheme.typography.titleMedium)
                    TextButton(onClick = { showResepDialog = true }) { Text("+ Tambah Obat") }
                }
                if (uiState.resepItems.isEmpty()) {
                    Text(
                        "Belum ada obat ditambahkan.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                } else {
                    uiState.resepItems.forEachIndexed { index, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(item.namaObat, fontWeight = FontWeight.Bold)
                                    Text("${item.jumlah}x • ${item.dosis} • ${CurrencyUtils.format(item.hargaSatuan * item.jumlah)}")
                                }
                                IconButton(onClick = { viewModel.hapusResepItem(index) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Hapus")
                                }
                            }
                        }
                    }
                }
            }

            uiState.errorMessage?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    viewModel.simpanPemeriksaan(
                        noRegistrasi = noRegistrasi,
                        tekananDarah = tekananDarah,
                        nadi = nadi.toIntOrNull() ?: 0,
                        suhuTubuh = suhuTubuh.toDoubleOrNull() ?: 0.0,
                        beratBadan = beratBadan.toDoubleOrNull() ?: 0.0,
                        keluhanUtama = keluhanUtama,
                        kodeICD10 = kodeICD10,
                        namaDiagnosa = namaDiagnosa,
                        anamnesis = anamnesis,
                        rencanaTatalaksana = rencanaTatalaksana
                    )
                },
                enabled = !uiState.isSaving && namaDiagnosa.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) { Text(if (uiState.isSaving) "Menyimpan..." else "Simpan Pemeriksaan & Kirim Resep") }
        }
    }

    if (showResepDialog) {
        TambahObatDialog(
            daftarObat = daftarObat,
            onDismiss = { showResepDialog = false },
            onTambah = { obat, dosis, jumlah ->
                viewModel.tambahResepItem(obat, dosis, jumlah)
                showResepDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TambahObatDialog(
    daftarObat: List<Obat>,
    onDismiss: () -> Unit,
    onTambah: (Obat, String, Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var obatDipilih by remember { mutableStateOf(daftarObat.firstOrNull()) }
    var dosis by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("1") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Obat ke Resep") },
        text = {
            Column {
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                    OutlinedTextField(
                        value = obatDipilih?.namaObat ?: "Pilih obat",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Obat") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        daftarObat.forEach { obat ->
                            DropdownMenuItem(
                                text = { Text("${obat.namaObat} (stok: ${obat.stok})") },
                                onClick = { obatDipilih = obat; expanded = false }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = dosis, onValueChange = { dosis = it },
                    label = { Text("Dosis (mis. 3x1 sehari)") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = jumlah, onValueChange = { jumlah = it.filter(Char::isDigit) },
                    label = { Text("Jumlah") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val obat = obatDipilih
                val jml = jumlah.toIntOrNull() ?: 1
                if (obat != null && dosis.isNotBlank() && jml > 0) {
                    onTambah(obat, dosis, jml)
                }
            }) { Text("Tambah") }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}
