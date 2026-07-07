package com.rsmbs.simrs.ui.pendaftaran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsmbs.simrs.SimrsApplication
import com.rsmbs.simrs.data.entity.Pasien
import com.rsmbs.simrs.ui.SimrsViewModelFactory
import com.rsmbs.simrs.ui.common.SectionCard
import com.rsmbs.simrs.ui.common.SimrsTopBar
import com.rsmbs.simrs.ui.theme.LogoGreen
import com.rsmbs.simrs.ui.theme.LogoNavy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendaftaranScreen(onBack: () -> Unit, onLihatAntrian: () -> Unit) {
    val app = LocalContext.current.applicationContext as SimrsApplication
    val viewModel: PendaftaranViewModel = viewModel(factory = SimrsViewModelFactory(app.repository))
    val uiState by viewModel.uiState.collectAsState()
    val semuaPasien by viewModel.semuaPasien.collectAsState()

    var nik by remember { mutableStateOf("") }
    var namaBaru by remember { mutableStateOf("") }
    var tanggalLahirBaru by remember { mutableStateOf("") }
    var jenisKelamin by remember { mutableStateOf("L") }
    var jenisPenjamin by remember { mutableStateOf("UMUM") }
    var poliDipilih by remember { mutableStateOf(DAFTAR_POLI.first()) }
    var noSep by remember { mutableStateOf("") }
    var poliExpanded by remember { mutableStateOf(false) }
    var pasienUntukQuickDaftar by remember { mutableStateOf<Pasien?>(null) }

    Scaffold(topBar = { SimrsTopBar("Pendaftaran Pasien", onBack) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Aksi cepat: langsung ke form pasien baru tanpa perlu cari NIK dulu
            Card(
                onClick = { viewModel.tampilkanFormPasienBaru() },
                colors = CardDefaults.cardColors(containerColor = LogoNavy),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Filled.PersonAdd, contentDescription = null, tint = androidx.compose.ui.graphics.Color.White)
                    Column {
                        Text(
                            "Daftarkan Pasien Baru",
                            color = androidx.compose.ui.graphics.Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Belum punya NRM? Isi data pasien langsung di sini.",
                            color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.85f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            SectionCard {
                Text("Cari Pasien Terdaftar (NIK)", style = MaterialTheme.typography.titleMedium)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = nik,
                        onValueChange = { nik = it },
                        label = { Text("NIK Pasien") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { if (nik.isNotBlank()) viewModel.cariPasien(nik) }) {
                        Icon(Icons.Filled.Search, contentDescription = "Cari")
                    }
                }
                uiState.pesan?.let {
                    Text(it, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(top = 8.dp))
                }
            }

            if (uiState.sudahDicari && uiState.pasienDitemukan != null) {
                val pasien = uiState.pasienDitemukan!!
                SectionCard {
                    Text("Data Pasien Ditemukan", style = MaterialTheme.typography.titleMedium)
                    Text("Nama: ${pasien.namaPasien}")
                    Text("No. Rekam Medis: ${pasien.noRekamMedis}")
                    Text("Jenis Penjamin: ${pasien.jenisPenjamin}")

                    PoliDropdown(poliDipilih, poliExpanded, onExpandChange = { poliExpanded = it }, onSelect = {
                        poliDipilih = it
                        poliExpanded = false
                    })

                    if (pasien.jenisPenjamin == "BPJS") {
                        OutlinedTextField(
                            value = noSep,
                            onValueChange = { noSep = it },
                            label = { Text("No. SEP (opsional)") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.daftarkanKunjunganPasienLama(
                                pasien.noRekamMedis, poliDipilih, noSep.ifBlank { null }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) { Text("Daftarkan Kunjungan") }
                }
            }

            if (uiState.sudahDicari && uiState.pasienDitemukan == null) {
                SectionCard {
                    Text("Formulir Pasien Baru", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = nik, onValueChange = { nik = it },
                        label = { Text("NIK") }, singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                    OutlinedTextField(
                        value = namaBaru, onValueChange = { namaBaru = it },
                        label = { Text("Nama Lengkap") }, singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                    OutlinedTextField(
                        value = tanggalLahirBaru, onValueChange = { tanggalLahirBaru = it },
                        label = { Text("Tanggal Lahir (yyyy-MM-dd)") }, singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        listOf("L", "P").forEach { opsi ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                RadioButton(
                                    selected = jenisKelamin == opsi,
                                    onClick = { jenisKelamin = opsi }
                                )
                                Text(if (opsi == "L") "Laki-laki" else "Perempuan")
                            }
                        }
                    }

                    JenisPenjaminDropdown(jenisPenjamin) { jenisPenjamin = it }
                    PoliDropdown(poliDipilih, poliExpanded, onExpandChange = { poliExpanded = it }, onSelect = {
                        poliDipilih = it
                        poliExpanded = false
                    })

                    if (jenisPenjamin == "BPJS") {
                        OutlinedTextField(
                            value = noSep,
                            onValueChange = { noSep = it },
                            label = { Text("No. SEP (opsional)") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.daftarPasienBaruDanDaftarkanKunjungan(
                                nik = nik,
                                nama = namaBaru,
                                tanggalLahir = tanggalLahirBaru,
                                jenisKelamin = jenisKelamin,
                                jenisPenjamin = jenisPenjamin,
                                poli = poliDipilih,
                                noSEP = noSep.ifBlank { null }
                            )
                        },
                        enabled = nik.isNotBlank() && namaBaru.isNotBlank() && tanggalLahirBaru.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) { Text("Simpan & Daftarkan Kunjungan") }
                }
            }

            if (uiState.berhasilDaftar) {
                SectionCard {
                    Text(
                        "✔ " + (uiState.pesan ?: "Pendaftaran berhasil."),
                        color = LogoGreen,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = onLihatAntrian,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) { Text("Lihat Antrian") }
                }
            }

            Button(onClick = onLihatAntrian, modifier = Modifier.fillMaxWidth()) {
                Text("Kelola Antrian Hari Ini")
            }

            if (semuaPasien.isNotEmpty()) {
                Text(
                    "Pasien Terdaftar (${semuaPasien.size})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                semuaPasien.forEach { pasien ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(pasien.namaPasien, fontWeight = FontWeight.Bold)
                                Text(
                                    "${pasien.noRekamMedis} • ${pasien.jenisPenjamin}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            TextButton(onClick = { pasienUntukQuickDaftar = pasien }) {
                                Text("Daftarkan Kunjungan")
                            }
                        }
                    }
                }
            }
        }
    }

    pasienUntukQuickDaftar?.let { pasien ->
        QuickDaftarDialog(
            pasien = pasien,
            onDismiss = { pasienUntukQuickDaftar = null },
            onKonfirmasi = { poli ->
                viewModel.daftarkanKunjunganPasienLama(pasien.noRekamMedis, poli, null)
                pasienUntukQuickDaftar = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuickDaftarDialog(pasien: Pasien, onDismiss: () -> Unit, onKonfirmasi: (String) -> Unit) {
    var poli by remember { mutableStateOf(DAFTAR_POLI.first()) }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Daftarkan Kunjungan") },
        text = {
            Column {
                Text("Pasien: ${pasien.namaPasien}", fontWeight = FontWeight.Bold)
                Text("NRM: ${pasien.noRekamMedis}", modifier = Modifier.padding(bottom = 8.dp))
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                    OutlinedTextField(
                        value = poli,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Poliklinik Tujuan") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DAFTAR_POLI.forEach { item ->
                            DropdownMenuItem(text = { Text(item) }, onClick = { poli = item; expanded = false })
                        }
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = { onKonfirmasi(poli) }) { Text("Daftarkan") } },
        dismissButton = { OutlinedButton(onClick = onDismiss) { Text("Batal") } }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PoliDropdown(
    selected: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onSelect: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandChange,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text("Poliklinik Tujuan") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onExpandChange(false) }) {
            DAFTAR_POLI.forEach { poli ->
                DropdownMenuItem(text = { Text(poli) }, onClick = { onSelect(poli) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JenisPenjaminDropdown(selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val opsi = listOf("UMUM", "BPJS", "ASURANSI")
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.padding(top = 8.dp)
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text("Jenis Penjamin") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            opsi.forEach { item ->
                DropdownMenuItem(text = { Text(item) }, onClick = { onSelect(item); expanded = false })
            }
        }
    }
}
