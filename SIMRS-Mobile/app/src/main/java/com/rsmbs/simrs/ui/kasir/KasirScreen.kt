package com.rsmbs.simrs.ui.kasir

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsmbs.simrs.SimrsApplication
import com.rsmbs.simrs.data.entity.TagihanQueueItem
import com.rsmbs.simrs.ui.SimrsViewModelFactory
import com.rsmbs.simrs.ui.common.EmptyState
import com.rsmbs.simrs.ui.common.FadeSlideIn
import com.rsmbs.simrs.ui.common.ListItemCard
import com.rsmbs.simrs.ui.common.SimrsTopBar
import com.rsmbs.simrs.ui.common.StatusChip
import com.rsmbs.simrs.ui.theme.AmberWarning
import com.rsmbs.simrs.ui.theme.GreenSuccess
import com.rsmbs.simrs.ui.theme.LogoGold
import com.rsmbs.simrs.ui.theme.PastelGold

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
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(antrian) { index, item ->
                    FadeSlideIn(delayIndex = index) {
                        val lunas = item.statusBayarTagihan == "LUNAS"
                        ListItemCard(
                            title = item.namaPasien,
                            subtitle = "${item.poliTujuan} • ${item.jenisPenjamin}",
                            icon = Icons.Filled.PointOfSale,
                            iconTint = LogoGold,
                            iconBackground = PastelGold,
                            trailing = { StatusChip(if (lunas) "Lunas" else "Belum Ditagih", if (lunas) GreenSuccess else AmberWarning) },
                            onClick = { onPilihKunjungan(item.noRegistrasi) }
                        )
                    }
                }
            }
        }
    }
}

private fun LazyListScope.itemsIndexed(
    list: List<TagihanQueueItem>,
    content: @Composable (Int, TagihanQueueItem) -> Unit
) {
    items(list.size) { index -> content(index, list[index]) }
}
