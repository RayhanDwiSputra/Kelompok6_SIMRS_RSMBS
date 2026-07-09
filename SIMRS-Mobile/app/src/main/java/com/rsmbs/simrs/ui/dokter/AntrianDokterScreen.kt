package com.rsmbs.simrs.ui.dokter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsmbs.simrs.SimrsApplication
import com.rsmbs.simrs.data.entity.AntrianItem
import com.rsmbs.simrs.ui.SimrsViewModelFactory
import com.rsmbs.simrs.ui.common.EmptyState
import com.rsmbs.simrs.ui.common.FadeSlideIn
import com.rsmbs.simrs.ui.common.ListItemCard
import com.rsmbs.simrs.ui.common.SimrsTopBar
import com.rsmbs.simrs.ui.common.StatusChip
import com.rsmbs.simrs.ui.theme.LogoMaroon
import com.rsmbs.simrs.ui.theme.PastelMaroon

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
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(antrian) { index, item ->
                    FadeSlideIn(delayIndex = index) {
                        ListItemCard(
                            title = item.namaPasien,
                            subtitle = "${item.poliTujuan} • NRM: ${item.noRekamMedis}",
                            icon = Icons.Filled.MedicalServices,
                            iconTint = LogoMaroon,
                            iconBackground = PastelMaroon,
                            trailing = { StatusChip("Antrian ${item.noAntrian}", LogoMaroon) },
                            onClick = { onPilihPasien(item.noRegistrasi) }
                        )
                    }
                }
            }
        }
    }
}

private fun LazyListScope.itemsIndexed(
    list: List<AntrianItem>,
    content: @Composable (Int, AntrianItem) -> Unit
) {
    items(list.size) { index -> content(index, list[index]) }
}
